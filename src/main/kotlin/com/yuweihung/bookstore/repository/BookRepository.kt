package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.Book
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 书籍的数据库访问接口
 */
interface BookRepository : JpaRepository<Book, Long> {
    fun findByNameLike(name: String): List<Book>

    fun findByPress_Id(id: Long): List<Book>

}
