package com.yuweihung.bookstore.bean.vo

import com.yuweihung.bookstore.bean.entity.User

class UserVo(user: User) {
    val id: Long?
    val username: String
    val gender: String

    init {
        id = user.id
        username = user.username
        gender = user.gender.value
    }
}
