package com.yuweihung.bookstore.response

/**
 * 异常的接口定义
 */
interface BaseErrorInterface {
    val code: Int
    val message: String
}
