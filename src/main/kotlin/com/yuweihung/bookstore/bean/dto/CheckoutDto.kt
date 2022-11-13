package com.yuweihung.bookstore.bean.dto

import jakarta.validation.constraints.Pattern

/**
 * 提交订单接收的参数
 */
data class CheckoutDto(
   val username: String,
   @get: Pattern(regexp = "\\d{11}", message = "手机号码格式错误")
   val phoneNumber: String,
   val address: String,
)
