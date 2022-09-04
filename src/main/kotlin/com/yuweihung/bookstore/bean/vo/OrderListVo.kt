package com.yuweihung.bookstore.bean.vo

import com.yuweihung.bookstore.bean.entity.Order
import org.springframework.data.domain.Page

/**
 * 订单分页查询结果
 */
class OrderListVo() {
    var totalPage: Int = 0
    var page: Int = 0
    var orders: List<OrderVo> = listOf()

    constructor(orders: Page<Order>, page: Int) : this() {
        this.totalPage = orders.totalPages
        this.orders = orders.content.map { OrderVo(it) }
        this.page = page
    }
}
