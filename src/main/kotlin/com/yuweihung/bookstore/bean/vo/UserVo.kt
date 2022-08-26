package com.yuweihung.bookstore.bean.vo

import com.fasterxml.jackson.annotation.JsonInclude
import com.yuweihung.bookstore.bean.entity.User

@JsonInclude(JsonInclude.Include.NON_NULL)
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
