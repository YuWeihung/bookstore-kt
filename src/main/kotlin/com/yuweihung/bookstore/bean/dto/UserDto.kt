package com.yuweihung.bookstore.bean.dto

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Size

/**
 * 注册接收的参数
 * @author Yu Weihong
 * @since 2022/7/25
 */
data class UserDto(
    @field: Size(min = 3, max = 20, message = "用户名长度须为 3-20 字符")
    val username: String,
    @field: Size(min = 6, max = 30, message = "密码长度须为 6-30 字符")
    val password: String,
    @field: Min(0) @field: Max(1)
    val gender: Byte,
)
