package com.yuweihung.bookstore.bean.vo

import com.fasterxml.jackson.annotation.JsonFormat
import com.yuweihung.bookstore.bean.entity.Order
import java.math.BigDecimal
import java.time.LocalDateTime

class OrderVo(order: Order) {
    val id: Long?
    val books: List<ItemVo>
    val totalPrice: BigDecimal

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createTime: LocalDateTime?

    init {
        id = order.id
        books = order.items.map { ItemVo(it.book.id, it.book.name, it.book.price, it.amount) }
        totalPrice = order.totalPrice
        createTime = order.createdTime
    }
}
