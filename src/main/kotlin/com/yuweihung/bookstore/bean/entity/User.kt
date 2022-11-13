package com.yuweihung.bookstore.bean.entity

import jakarta.persistence.*

/**
 * 用户实体类
 */
@Entity
@Table(
    name = "\"user\"",
    indexes = [
        Index(name = "uni_username", columnList = "username", unique = true),
    ]
)
class User(
    @Column(name = "username", nullable = false)
    var username: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "gender", nullable = false)
    var gender: Gender,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    ) var roles: MutableSet<Role> = mutableSetOf(),

    @OneToOne
    var cart: Cart,
) : BaseEntity() {

    @OneToMany(mappedBy = "user")
    var addresses: MutableSet<Address> = mutableSetOf()

    @OneToMany(mappedBy = "user")
    var orders: MutableSet<Order> = mutableSetOf()
}
