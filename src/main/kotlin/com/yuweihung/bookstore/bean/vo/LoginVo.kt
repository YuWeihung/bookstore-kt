package com.yuweihung.bookstore.bean.vo

/**
 * 登录返回的信息
 */
data class LoginVo(
    val username: String,
    val authorities: List<String>,
    val accessToken: String,
    val refreshToken: String,
)
