package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.Press
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 出版社的数据库访问接口
 */
interface PressRepository : JpaRepository<Press, Long> {

    fun findByName(name: String): Press?

}
