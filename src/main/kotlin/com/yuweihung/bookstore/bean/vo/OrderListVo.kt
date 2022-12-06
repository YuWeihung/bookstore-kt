package com.yuweihung.bookstore.bean.vo

import com.yuweihung.bookstore.bean.entity.Order
import org.springframework.data.domain.Page

/**
 * 订单分页查询结果
 */
class OrderListVo(orders: Page<Order>, val page: Int) {
    val totalPages: Int
    val orders: List<OrderVo>

    init {
        this.totalPages = orders.totalPages
        this.orders = orders.content.map { OrderVo(it) }
    }
}
