package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.dto.CartDto
import com.yuweihung.bookstore.bean.entity.Cart
import com.yuweihung.bookstore.bean.entity.Item
import com.yuweihung.bookstore.common.ErrorCode
import com.yuweihung.bookstore.common.ErrorException
import com.yuweihung.bookstore.repository.BookRepository
import com.yuweihung.bookstore.repository.CartRepository
import com.yuweihung.bookstore.repository.ItemRepository
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
    fun getCart(cartId: Long): Cart {
        val cart = cartRepository.findById(cartId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        if (cart.items.isEmpty()) {
            throw ErrorException(ErrorCode.CONTENT_IS_EMPTY)
        } else {
            return cart
        }
    }

    /**
     * 向购物车中添加项
     */
    fun addItem(cartDto: CartDto): Cart {
        val book = bookRepository.findById(cartDto.bookId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        // 数量不足不能购买
        if (book.inventory < cartDto.amount) {
            throw ErrorException(ErrorCode.INVENTORY_NOT_ENOUGH)
        }
        val item = Item(book, cartDto.amount)
        itemRepository.save(item)
        val cart = cartRepository.findById(cartDto.cartId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
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
    fun removeItem(cartDto: CartDto): Cart {
        val cart = cartRepository.findById(cartDto.cartId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        // 如果购物车中不存在该书籍不允许移除
        val contains = cart.items.filter { it.book.id == cartDto.bookId }
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
    fun modifyItem(cartDto: CartDto): Cart {
        val cart = cartRepository.findById(cartDto.cartId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        val book = bookRepository.findById(cartDto.bookId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        // 数量不足不能购买
        if (book.inventory < cartDto.amount) {
            throw ErrorException(ErrorCode.INVENTORY_NOT_ENOUGH)
        }
        // 如果购物车中不存在该书籍不允许修改
        val contains = cart.items.filter { it.book == book }
        if (contains.isEmpty()) {
            throw ErrorException(ErrorCode.CART_ERROR)
        } else {
            val item = contains[0]
            item.amount = cartDto.amount
            itemRepository.save(item)
            cart.calculatePrice()
            return cartRepository.save(cart)
        }
    }

}
