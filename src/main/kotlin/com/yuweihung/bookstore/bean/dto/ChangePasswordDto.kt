package com.yuweihung.bookstore.bean.dto

import javax.validation.constraints.Size

/**
 * 更改密码接收的参数
 * @author Yu Weihong
 * @since 2022/7/25
 */
data class ChangePasswordDto(
    @field: Size(min = 3, max = 20, message = "用户名长度须为 3-20 字符")
    val username: String,
    @field: Size(min = 6, max = 30, message = "密码长度须为 6-30 字符")
    val oldPassword: String,
    @field: Size(min = 6, max = 30, message = "密码长度须为 6-30 字符")
    val newPassword: String,
)
