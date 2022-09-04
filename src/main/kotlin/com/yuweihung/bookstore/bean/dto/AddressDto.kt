package com.yuweihung.bookstore.bean.dto

import javax.validation.constraints.Pattern

/**
 * 修改收货地址接收的参数
 */
data class AddressDto(
    val addressId: Long = -1,
    val username: String = "",
    @get: Pattern(regexp = "\\d{11}", message = "手机号码格式错误")
    val phoneNumber: String = "",
    val address: String = "",
)
