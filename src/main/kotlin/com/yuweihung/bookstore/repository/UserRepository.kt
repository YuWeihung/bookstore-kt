package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 用户的数据库访问接口
 * @author Yu Weihong
 * @since 2022/7/15
 */
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

    fun countByUsername(username: String): Long

}
