package com.yuweihung.bookstore.bean.entity

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

/**
 * 用户实体类
 * @author Yu Weihong
 * @since 2022/7/15
 */
@Entity
@Table(name = "\"user\"")
class User(
    var username: String,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var password: String,

    // 性别： 0 男性 1 女性
    var gender: Byte = 0,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    ) var roles: MutableSet<Role> = mutableSetOf(),
) : BaseEntity()
