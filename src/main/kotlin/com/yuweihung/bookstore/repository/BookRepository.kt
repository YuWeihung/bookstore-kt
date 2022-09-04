package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 书籍的数据库访问接口
 */
interface BookRepository : JpaRepository<Book, Long> {

    fun countByIsbn(isbn: String): Long

    fun findByInventoryGreaterThanOrderByIdAsc(inventory: Int, pageable: Pageable): Page<Book>

    fun findByPress_IdAndInventoryGreaterThanOrderByIdAsc(id: Long, inventory: Int, pageable: Pageable): Page<Book>

    fun findByAuthors_IdAndInventoryGreaterThanOrderByIdAsc(id: Long, inventory: Int, pageable: Pageable): Page<Book>

    fun findByNameLikeAndInventoryGreaterThanOrderByIdAsc(name: String, inventory: Int, pageable: Pageable): Page<Book>

}
