package com.yuweihung.bookstore.bean.dto

import java.io.Serializable

/**
 * 登录接收的参数
 * @author Yu Weihong
 * @since 2022/7/25
 */
data class LoginDto(
    val username: String = "",
    val password: String = "",
) : Serializable
