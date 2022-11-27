package com.yuweihung.bookstore.bean.dto

import jakarta.validation.constraints.Size

/**
 * 更改密码接收的参数
 */
data class ChangePasswordDto(
    @get: Size(min = 6, max = 30, message = "密码长度须为 6-30 字符")
    val oldPassword: String,
    @get: Size(min = 6, max = 30, message = "密码长度须为 6-30 字符")
    val newPassword: String,
)
