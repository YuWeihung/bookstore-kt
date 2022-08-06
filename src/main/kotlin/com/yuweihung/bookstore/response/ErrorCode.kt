package com.yuweihung.bookstore.response

/**
 * 异常的错误码的枚举定义
 * @author Yu Weihong
 * @since 2022/7/15
 */
enum class ErrorCode(override val code: Int, override val message: String) : BaseErrorInterface {
    PARAM_NOT_VALID(4001, "参数校验错误"),
    USER_ALREADY_EXIST(4002, "用户名已存在"),
    PASSWORD_WRONG(4003, "用户名或密码错误"),
    USER_NOT_LOGIN(4011, "用户未登录"),
    FORBIDDEN(4031, "用户无权访问"),
    USER_NOT_EXIST(4041, "用户名不存在"),
    ROLE_NOT_EXIST(4042, "角色权限不存在"),
    UNKNOWN_ERROR(9999, "发生了未知错误"),
}
