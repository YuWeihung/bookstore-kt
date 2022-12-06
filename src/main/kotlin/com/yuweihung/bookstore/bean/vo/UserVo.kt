package com.yuweihung.bookstore.bean.vo

import com.yuweihung.bookstore.bean.entity.User

/**
 * 用户信息
 */
class UserVo(user: User) {
    val userId: Long?
    val username: String
    val gender: String
    val roles: List<String>
    val cartId: Long?
    val addresses: List<AddressVo>

    init {
        this.userId = user.id
        this.username = user.username
        this.gender = user.gender.value
        this.roles = user.roles.map { it.name }
        this.cartId = user.cart.id
        this.addresses = user.addresses.map { AddressVo(it.id, it.phoneNumber, it.address) }
    }
}
