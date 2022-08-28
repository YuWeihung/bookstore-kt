package com.yuweihung.bookstore.bean.vo

import com.yuweihung.bookstore.bean.entity.User

/**
 * 向前端返回的用户信息
 */
class UserVo() {
    var id: Long? = null
    var username: String = ""
    var gender: String = ""
    var roles: List<String> = listOf()
    var cartId: Long? = null

    constructor(user: User) : this() {
        id = user.id
        username = user.username
        gender = user.gender.value
        roles = user.roles.map { it.name }
        cartId = user.cart.id
    }
}
