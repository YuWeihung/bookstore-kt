package com.yuweihung.bookstore.bean.vo

import com.yuweihung.bookstore.bean.entity.User

/**
 * 用户信息
 */
class UserVo() {
    var userId: Long? = null
    var username: String = ""
    var gender: String = ""
    var roles: List<String> = listOf()
    var cartId: Long? = null
    var addresses: List<AddressVo> = listOf()

    constructor(user: User) : this() {
        userId = user.id
        username = user.username
        gender = user.gender.value
        roles = user.roles.map { it.name }
        cartId = user.cart.id
        addresses = user.addresses.map { AddressVo(it.id, it.phoneNumber, it.address) }
    }
}
