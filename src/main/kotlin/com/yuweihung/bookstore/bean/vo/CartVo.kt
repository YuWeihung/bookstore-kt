package com.yuweihung.bookstore.bean.vo

import com.yuweihung.bookstore.bean.entity.Cart
import java.math.BigDecimal

class CartVo(cart: Cart) {
    val id: Long?
    val books: List<ItemVo>
    val totalPrice: BigDecimal

    init {
        id = cart.id
        books = cart.items.map { ItemVo(it.book.id, it.book.name, it.book.price, it.amount) }
        totalPrice = cart.totalPrice
    }
}
