package com.yuweihung.bookstore.bean.entity

import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table

/**
 * 用户权限实体类
 */
@Entity
@Table(name = "role")
class Role(
    var name: String,
) : BaseEntity() {

    @ManyToMany(mappedBy = "roles")
    var users: MutableSet<User> = mutableSetOf()
}
