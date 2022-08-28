package com.yuweihung.bookstore.response

/**
 * 异常的错误码的枚举定义
 */
enum class ErrorCode(override val code: Int, override val message: String) : BaseErrorInterface {
    PARAM_NOT_VALID(4001, "参数校验错误"),
    USER_ALREADY_EXIST(4002, "用户名已存在"),
    PASSWORD_WRONG(4003, "用户名或密码错误"),
    INVENTORY_NOT_ENOUGH(4004, "商品数量不足"),
    CART_ERROR(4005, "修改购物车错误"),
    BOOK_ALREADY_EXIST(4006, "书籍已存在"),
    ALREADY_ADMIN(4007, "该用户已经是管理员"),
    USER_NOT_LOGIN(4011, "用户未登录"),
    FORBIDDEN(4031, "用户无权访问"),
    USER_NOT_EXIST(4041, "用户名不存在"),
    ROLE_NOT_EXIST(4042, "角色权限不存在"),
    NO_SEARCH_RESULT(4043, "无搜索结果"),
    NO_SUCH_ITEM(4044, "无该项数据"),
    CONTENT_IS_EMPTY(4045, "内容为空"),
    SERVER_ERROR(5001, "服务器错误"),
    UNKNOWN_ERROR(9999, "发生了未知错误"),
}
