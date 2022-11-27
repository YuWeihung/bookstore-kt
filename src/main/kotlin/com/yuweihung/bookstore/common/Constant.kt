package com.yuweihung.bookstore.common

/**
 * 常量定义类
 */
class Constant {
    companion object {
        // 分页查询页数
        const val PAGE_SIZE = 10

        // redis 缓存时间
        const val CACHE_EXPIRATION_TIME = 5L

        // JWT token 有效期
        const val TOKEN_EXPIRATION_TIME = 36000L
    }
}
