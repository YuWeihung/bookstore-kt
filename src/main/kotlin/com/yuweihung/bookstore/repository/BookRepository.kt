package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 书籍的数据库访问接口
 */
interface BookRepository : JpaRepository<Book, Long> {
    fun findByInventoryGreaterThan(inventory: Int, pageable: Pageable): Page<Book>

    fun findByPress_IdAndInventoryGreaterThan(id: Long, inventory: Int, pageable: Pageable): Page<Book>

    fun findByAuthors_IdAndInventoryGreaterThan(id: Long, inventory: Int, pageable: Pageable): Page<Book>

    fun findByNameLikeAndInventoryGreaterThan(name: String, inventory: Int, pageable: Pageable): Page<Book>

}
