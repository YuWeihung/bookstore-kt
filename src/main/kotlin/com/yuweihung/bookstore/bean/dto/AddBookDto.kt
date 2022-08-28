package com.yuweihung.bookstore.bean.dto

import javax.validation.constraints.Max
import javax.validation.constraints.Min

/**
 * 添加书籍接收的参数
 */
data class AddBookDto(
    val name: String,
    val isbn: String,
    val price: String,
    @get: Min(0)
    val pageCount: Int,
    val publishYear: Int,
    @get: Max(12) @get: Min(1)
    val publishMonth: Int,
    val inventory: Int,
    val press: String,
    val authors: List<String>,
)
