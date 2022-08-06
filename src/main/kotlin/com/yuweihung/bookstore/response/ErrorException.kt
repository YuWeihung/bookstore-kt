package com.yuweihung.bookstore.response

/**
 * 自定义异常的实现
 * @author Yu Weihong
 * @since 2022/7/15
 */
class ErrorException(errorCode: BaseErrorInterface) : RuntimeException(), BaseErrorInterface {
    override val code = errorCode.code
    override val message = errorCode.message
}
