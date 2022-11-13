package com.yuweihung.bookstore.bean.entity

import jakarta.persistence.*

/**
 * 用户权限实体类
 */
@Entity
@Table(
    name = "role",
    indexes = [
        Index(name = "uni_name", columnList = "name", unique = true),
    ]
)
class Role(
    @Column(name = "name", nullable = false)
    var name: String,
) : BaseEntity() {

    @ManyToMany(mappedBy = "roles")
    var users: MutableSet<User> = mutableSetOf()
}
