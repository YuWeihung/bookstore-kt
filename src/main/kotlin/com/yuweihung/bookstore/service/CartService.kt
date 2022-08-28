package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.entity.Cart
import com.yuweihung.bookstore.bean.entity.Item
import com.yuweihung.bookstore.repository.BookRepository
import com.yuweihung.bookstore.repository.CartRepository
import com.yuweihung.bookstore.repository.ItemRepository
import com.yuweihung.bookstore.response.ErrorCode
import com.yuweihung.bookstore.response.ErrorException
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger { }

/**
 * 购物车相关的服务类
 */
@Transactional
@Service
class CartService(
    val bookRepository: BookRepository,
    val itemRepository: ItemRepository,
    val cartRepository: CartRepository,
) {
    /**
     * 获取用户购物车内容
     */
    fun getCart(userId: Long): Cart {
        val cart = cartRepository.findByUser_Id(userId)
        if (cart == null) {
            throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        } else if (cart.items.isEmpty()) {
            throw ErrorException(ErrorCode.CONTENT_IS_EMPTY)
        } else {
            return cart
        }
    }

    /**
     * 向购物车中添加项
     */
    fun addItem(cartId: Long, bookId: Long, amount: Int): Cart {
        val book = bookRepository.findById(bookId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        // 数量不足不能购买
        if (book.inventory < amount) {
            throw ErrorException(ErrorCode.INVENTORY_NOT_ENOUGH)
        }
        val item = Item(book, amount)
        itemRepository.save(item)
        val cart = cartRepository.findById(cartId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        // 如果购物车中已存在该书籍，不允许再添加
        val count = cart.items.count { it.book == book }
        if (count == 0) {
            cart.items.add(item)
            cart.calculatePrice()
            return cartRepository.save(cart)
        } else {
            throw ErrorException(ErrorCode.CART_ERROR)
        }
    }

    /**
     * 从购物车中移除商品
     */
    fun removeItem(cartId: Long, bookId: Long): Cart {
        val cart = cartRepository.findById(cartId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        val book = bookRepository.findById(bookId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        // 如果购物车中不存在该书籍不允许移除
        val contains = cart.items.filter { it.book == book }
        if (contains.isEmpty()) {
            throw ErrorException(ErrorCode.CART_ERROR)
        } else {
            val item = contains[0]
            cart.items.remove(item)
            itemRepository.delete(item)
            cart.calculatePrice()
            return cartRepository.save(cart)
        }
    }

    /**
     * 修改购物车中商品数量
     */
    fun modifyItem(cartId: Long, bookId: Long, amount: Int): Cart {
        val cart = cartRepository.findById(cartId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        val book = bookRepository.findById(bookId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        // 数量不足不能购买
        if (book.inventory < amount) {
            throw ErrorException(ErrorCode.INVENTORY_NOT_ENOUGH)
        }
        // 如果购物车中不存在该书籍不允许修改
        val contains = cart.items.filter { it.book == book }
        if (contains.isEmpty()) {
            throw ErrorException(ErrorCode.CART_ERROR)
        } else {
            val item = contains[0]
            item.amount = amount
            itemRepository.save(item)
            cart.calculatePrice()
            return cartRepository.save(cart)
        }
    }

}
