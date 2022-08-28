package com.yuweihung.bookstore.bean.dto

/**
 * 修改购物车接收的参数
 */
data class CartDto(
    val cartId: Long,
    val bookId: Long,
    val amount: Int = 0,
)
