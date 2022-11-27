package com.yuweihung.bookstore.bean.vo

/**
 * 登录返回的信息
 */
class LoginVo() {
    var username: String = ""
    var authorities: List<String> = listOf()
    var token: String = ""

    constructor(username: String, authorities: List<String>, token: String) : this() {
        this.username = username
        this.authorities = authorities
        this.token = token
    }
}