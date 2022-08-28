package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.entity.Item
import com.yuweihung.bookstore.bean.entity.Order
import com.yuweihung.bookstore.repository.CartRepository
import com.yuweihung.bookstore.repository.ItemRepository
import com.yuweihung.bookstore.repository.OrderRepository
import com.yuweihung.bookstore.repository.UserRepository
import com.yuweihung.bookstore.response.ErrorCode
import com.yuweihung.bookstore.response.ErrorException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
    fun submitOrder(userId: Long, cartId: Long): Order {
        val user = userRepository.findById(userId).orElse(null) ?: throw ErrorException(ErrorCode.USER_NOT_EXIST)
        val cart = cartRepository.findById(cartId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
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
        val order = Order(user, items, cart.totalPrice)
        // 清空购物车，并减少商品库存
        for (item in cartItems) {
            item.book.inventory -= item.amount
            itemRepository.save(item)
            cart.items.remove(item)
            itemRepository.delete(item)
        }
        cart.calculatePrice()
        cartRepository.save(cart)
        return orderRepository.save(order)
    }

    /**
     * 获取用户所有订单
     */
    fun getOrdersByUser(userId: Long): List<Order> {
        val orders = orderRepository.findByUser_Id(userId)
        if (orders.isEmpty()) {
            throw ErrorException(ErrorCode.CONTENT_IS_EMPTY)
        } else {
            return orders
        }
    }

    /**
     * 获取订单详情
     */
    fun getOrderDetail(orderId: Long): Order {
        return orderRepository.findById(orderId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
    }
}
