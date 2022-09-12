package com.yuweihung.bookstore.bean.dto

import javax.validation.constraints.Pattern

/**
 * 直接购买指定书籍接收的参数
 */
data class BuyBookDto(
    val username: String,
    val bookId: Long,
    @get: Pattern(regexp = "\\d{11}", message = "手机号码格式错误")
    val phoneNumber: String,
    val address: String,
)
