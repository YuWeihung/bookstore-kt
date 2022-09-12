package com.yuweihung.bookstore.bean.vo

import com.fasterxml.jackson.annotation.JsonFormat
import com.yuweihung.bookstore.bean.entity.Order
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * 订单信息
 */
class OrderVo() {
    var orderId: Long? = null
    var username: String = ""
    var books: List<ItemVo> = listOf()
    var totalPrice: BigDecimal = BigDecimal("0.00")
    var phoneNumber: String = ""
    var address: String = ""
    var status: String = ""

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createTime: LocalDateTime? = null

    constructor(order: Order) : this() {
        orderId = order.id
        username = order.user.username
        books = order.items.map { ItemVo(it.book.id, it.book.name, it.book.price, it.amount) }
        totalPrice = order.totalPrice
        createTime = order.createdTime
        phoneNumber = order.phoneNumber
        address = order.address
        status = order.status.value
    }
}
