package com.yuweihung.bookstore.bean.dto

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

/**
 * 注册接收的参数
 */
data class RegisterDto(
    @get: Size(min = 3, max = 20, message = "用户名长度须为 3-20 字符")
    val username: String,
    @get: Size(min = 6, max = 30, message = "密码长度须为 6-30 字符")
    val password: String,
    @get: Pattern(regexp = "[MF]", message = "性别必须为男或女")
    val gender: String,
)
