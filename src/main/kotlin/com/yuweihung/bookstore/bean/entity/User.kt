package com.yuweihung.bookstore.bean.entity

import javax.persistence.*

/**
 * 用户实体类
 */
@Entity
@Table(name = "\"user\"")
class User(
    var username: String,

    var password: String,

    var gender: Gender,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    ) var roles: MutableSet<Role> = mutableSetOf(),

    @OneToMany(mappedBy = "user")
    var orders: MutableSet<Order> = mutableSetOf(),

    @OneToOne
    var cart: Cart? = null,
) : BaseEntity()
