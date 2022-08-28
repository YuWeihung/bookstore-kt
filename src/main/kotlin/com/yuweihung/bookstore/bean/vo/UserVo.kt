package com.yuweihung.bookstore.bean.vo

import com.yuweihung.bookstore.bean.entity.User

class UserVo(user: User) {
    val id: Long?
    val username: String
    val gender: String
    val roles: List<String>

    init {
        id = user.id
        username = user.username
        gender = user.gender.value
        roles = user.roles.map { it.name }
    }
}
