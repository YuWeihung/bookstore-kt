package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.entity.Item
import com.yuweihung.bookstore.bean.entity.Order
import com.yuweihung.bookstore.repository.CartRepository
import com.yuweihung.bookstore.repository.ItemRepository
import com.yuweihung.bookstore.repository.OrderRepository
import com.yuweihung.bookstore.repository.UserRepository
import com.yuweihung.bookstore.response.ErrorCode
import com.yuweihung.bookstore.response.ErrorException
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
    companion object {
        const val PAGE_SIZE = 10
    }

    /**
     * 提交订单
     */
    fun submitOrder(username: String): Order {
        val user = userRepository.findByUsername(username) ?: throw ErrorException(ErrorCode.USER_NOT_EXIST)
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
    fun getOrdersByUser(username: String, page: Int): List<Order> {
        val pageable = PageRequest.of(page, PAGE_SIZE)
        val orders = orderRepository.findByUser_Username(username, pageable)
        if (orders.isEmpty) {
            throw ErrorException(ErrorCode.CONTENT_IS_EMPTY)
        } else {
            return orders.content
        }
    }

    /**
     * 获取订单详情
     */
    fun getOrderDetail(orderId: Long): Order {
        return orderRepository.findById(orderId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
    }
}
