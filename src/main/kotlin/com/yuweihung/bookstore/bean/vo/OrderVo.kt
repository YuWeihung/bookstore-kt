package com.yuweihung.bookstore.bean.vo

import com.fasterxml.jackson.annotation.JsonFormat
import com.yuweihung.bookstore.bean.entity.Order
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * 向前端返回的订单信息
 */
class OrderVo() {
    var id: Long? = null
    var books: List<ItemVo> = listOf()
    var totalPrice: BigDecimal = BigDecimal("0.00")

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createTime: LocalDateTime? = null

    constructor(order: Order) : this() {
        id = order.id
        books = order.items.map { ItemVo(it.book.id, it.book.name, it.book.price, it.amount) }
        totalPrice = order.totalPrice
        createTime = order.createdTime
    }
}
