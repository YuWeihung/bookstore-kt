package com.yuweihung.bookstore.bean.vo

import com.yuweihung.bookstore.bean.entity.Cart
import java.math.BigDecimal

/**
 * 购物车信息
 */
class CartVo(cart: Cart) {
    val cartId: Long?
    val books: List<ItemVo>
    val totalPrice: BigDecimal

    init {
        this.cartId = cart.id
        this.books = cart.items.map { ItemVo(it.book.id, it.book.name, it.book.price, it.amount) }
        this.totalPrice = cart.totalPrice
    }
}
