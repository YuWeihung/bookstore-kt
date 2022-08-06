package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.Book
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 书籍的数据库访问接口
 * @author Yu Weihong
 * @since 2022/7/15
 */
interface BookRepository : JpaRepository<Book, Long> {

    fun findByName(name: String): Book?

}
