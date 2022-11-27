package com.yuweihung.bookstore.bean.dto

import jakarta.validation.constraints.Size

/**
 * 登录接收的参数
 */
data class LoginDto(
    @get: Size(min = 3, max = 20, message = "用户名长度须为 3-20 字符")
    val username: String,
    @get: Size(min = 6, max = 30, message = "密码长度须为 6-30 字符")
    val password: String,
)
