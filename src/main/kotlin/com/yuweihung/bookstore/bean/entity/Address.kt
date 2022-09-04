package com.yuweihung.bookstore.bean.entity

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 收货地址的实体类
 */
@Entity
@Table(name = "address")
class Address(
    var phoneNumber: String,
    var address: String,
    @ManyToOne
    var user: User,
) : BaseEntity()
