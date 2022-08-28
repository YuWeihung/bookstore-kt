package com.yuweihung.bookstore.bean.dto

data class CartDto(
    val cartId: Long,
    val bookId: Long,
    val amount: Int = 0,
)
