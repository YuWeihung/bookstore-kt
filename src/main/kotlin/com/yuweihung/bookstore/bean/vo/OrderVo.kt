package com.yuweihung.bookstore.bean.vo

import com.fasterxml.jackson.annotation.JsonFormat
import com.yuweihung.bookstore.bean.entity.Order
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * 订单信息
 */
class OrderVo(order: Order) {
    val orderId: Long?
    val username: String
    val books: List<ItemVo>
    val totalPrice: BigDecimal
    val phoneNumber: String
    val address: String
    val status: String

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createTime: LocalDateTime?

    init {
        this.orderId = order.id
        this.username = order.user.username
        this.books = order.items.map { ItemVo(it.book.id, it.book.name, it.book.price, it.amount) }
        this.totalPrice = order.totalPrice
        this.createTime = order.createdTime
        this.phoneNumber = order.phoneNumber
        this.address = order.address
        this.status = order.status.value
    }
}
