package com.yuweihung.bookstore.bean.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 收货地址的实体类
 */
@Entity
@Table(name = "address")
class Address(
    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String,
    @Column(name = "address", nullable = false)
    var address: String,
    @ManyToOne
    var user: User,
) : BaseEntity()
