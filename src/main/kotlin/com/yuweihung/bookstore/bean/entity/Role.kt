package com.yuweihung.bookstore.bean.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table

/**
 * 用户权限实体
 * @author Yu Weihong
 * @since 2022/7/15
 */
@Entity
@Table(name = "role")
class Role(
    var name: String = "",
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    var users: MutableSet<User> = mutableSetOf<User>(),
) : BaseEntity()
