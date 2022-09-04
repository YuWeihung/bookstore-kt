package com.yuweihung.bookstore.bean.entity

/**
 * 订单状态枚举类
 */
enum class OrderStatus(val value: String) {
    NOT_PAID("待付款"),
    NOT_DELIVERED("待收货"),
    COMPLETED("已完成"),
    CANCELLED("已取消"),
}
