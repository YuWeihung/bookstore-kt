package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 用户权限的数据库访问接口
 */
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: String): Role?

}
