package com.yuweihung.bookstore.bean.vo

import com.yuweihung.bookstore.bean.entity.Cart
import java.math.BigDecimal

/**
 * 购物车信息
 */
class CartVo() {
    var cartId: Long? = null
    var books: List<ItemVo> = listOf()
    var totalPrice: BigDecimal = BigDecimal("0.00")

    constructor(cart: Cart) : this() {
        cartId = cart.id
        books = cart.items.map { ItemVo(it.book.id, it.book.name, it.book.price, it.amount) }
        totalPrice = cart.totalPrice
    }
}
