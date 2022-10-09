package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 用户的数据库访问接口
 */
interface UserRepository : JpaRepository<User, Long> {
    // 根据用户名查询
    fun findByUsername(username: String): User?

    // 根据用户名计数
    fun countByUsername(username: String): Long

}
