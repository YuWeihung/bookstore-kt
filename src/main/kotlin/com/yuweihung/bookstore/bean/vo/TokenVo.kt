package com.yuweihung.bookstore.bean.vo

/**
 * JWT Token
 */
data class TokenVo(
    val accessToken: String,
    val refreshToken: String,
)
