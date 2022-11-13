package com.yuweihung.bookstore.bean.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

/**
 * 添加书籍接收的参数
 */
data class BookDto(
    val name: String,
    val isbn: String,
    val price: String,
    @get: Min(0, message = "页数不能为负")
    val page: Int,
    val publishYear: Int,
    @get: Min(1, message = "月份非法") @get: Max(12, message = "月份非法")
    val publishMonth: Int,
    @get: Min(0, message = "库存不能为负")
    val inventory: Int,
    val press: String,
    val authors: List<String>,
)
