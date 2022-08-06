package com.yuweihung.bookstore.response

/**
 * 异常的接口定义
 * @author Yu Weihong
 * @since 2022/7/15
 */
interface BaseErrorInterface {
    val code: Int
    val message: String
}
