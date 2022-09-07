package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.dto.OrderDto
import com.yuweihung.bookstore.bean.entity.Item
import com.yuweihung.bookstore.bean.entity.Order
import com.yuweihung.bookstore.bean.entity.OrderStatus
import com.yuweihung.bookstore.bean.vo.OrderListVo
import com.yuweihung.bookstore.bean.vo.OrderVo
import com.yuweihung.bookstore.common.Constant
import com.yuweihung.bookstore.common.ErrorCode
import com.yuweihung.bookstore.common.ErrorException
import com.yuweihung.bookstore.repository.CartRepository
import com.yuweihung.bookstore.repository.ItemRepository
import com.yuweihung.bookstore.repository.OrderRepository
import com.yuweihung.bookstore.repository.UserRepository
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger { }

/**
 * 订单相关的服务类
 */
@Service
@Transactional
class OrderService(
    val userRepository: UserRepository,
    val cartRepository: CartRepository,
    val itemRepository: ItemRepository,
    val orderRepository: OrderRepository,
) {

    /**
     * 提交订单
     */
    fun submitOrder(orderDto: OrderDto): OrderVo {
        val user = userRepository.findByUsername(orderDto.username) ?: throw ErrorException(ErrorCode.USER_NOT_EXIST)
        val cart = user.cart
        if (cart.items.isEmpty()) {
            throw ErrorException(ErrorCode.CONTENT_IS_EMPTY)
        }
        val items = mutableSetOf<Item>()
        val cartItems = cart.items.toSet()
        for (item in cartItems) {
            if (item.book.inventory < item.amount) {
                throw ErrorException(ErrorCode.INVENTORY_NOT_ENOUGH)
            }
            val orderItem = Item(item.book, item.amount)
            itemRepository.save(orderItem)
            items.add(orderItem)
        }
        val order = Order(user, items, cart.totalPrice, orderDto.phoneNumber, orderDto.address)
        // 清空购物车，并减少商品库存
        for (item in cartItems) {
            item.book.inventory -= item.amount
            itemRepository.save(item)
            cart.items.remove(item)
            itemRepository.delete(item)
        }
        cart.calculatePrice()
        cartRepository.save(cart)
        val result = orderRepository.save(order)
        return OrderVo(result)
    }

    /**
     * 获取用户所有订单
     */
    fun getOrdersByUser(username: String, page: Int): OrderListVo {
        val pageable = PageRequest.of(page - 1, Constant.PAGE_SIZE)
        val orders = orderRepository.findByUser_UsernameOrderByIdAsc(username, pageable)
        if (orders.isEmpty) {
            throw ErrorException(ErrorCode.CONTENT_IS_EMPTY)
        } else {
            return OrderListVo(orders, page)
        }
    }

    /**
     * 获取订单详情
     */
    fun getOrderDetail(orderId: Long): OrderVo {
        val result = orderRepository.findById(orderId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        return OrderVo(result)
    }

    /**
     * 订单支付
     */
    fun payOrder(orderId: Long): OrderVo {
        val order = orderRepository.findById(orderId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        if (order.status == OrderStatus.NOT_PAID) {
            order.status = OrderStatus.NOT_DELIVERED
        } else {
            throw ErrorException(ErrorCode.ORDER_STATUS_ERROR)
        }
        val result = orderRepository.save(order)
        return OrderVo(result)
    }

    /**
     * 订单确认收货
     */
    fun completeOrder(orderId: Long): OrderVo {
        val order = orderRepository.findById(orderId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        if (order.status == OrderStatus.NOT_DELIVERED) {
            order.status = OrderStatus.COMPLETED
        } else {
            throw ErrorException(ErrorCode.ORDER_STATUS_ERROR)
        }
        val result = orderRepository.save(order)
        return OrderVo(result)
    }

    /**
     * 取消订单
     */
    fun cancelOrder(orderId: Long): OrderVo {
        val order = orderRepository.findById(orderId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        /**
         * 将订单内书籍加回库存
         */
        for (item in order.items) {
            item.book.inventory += item.amount
            itemRepository.save(item)
        }
        if (order.status != OrderStatus.CANCELLED && order.status != OrderStatus.COMPLETED) {
            order.status = OrderStatus.CANCELLED
        } else {
            throw ErrorException(ErrorCode.ORDER_STATUS_ERROR)
        }
        val result = orderRepository.save(order)
        return OrderVo(result)
    }
}
