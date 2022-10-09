package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 书籍的数据库访问接口
 */
interface BookRepository : JpaRepository<Book, Long> {
    // 根据标题模糊查询
    fun findByNameLikeAndInventoryGreaterThanOrderByIdAsc(name: String, inventory: Int, pageable: Pageable): Page<Book>

    // 根据 ISBN 计数
    fun countByIsbn(isbn: String): Long

    // 分页查询所有库存大于 0 的书籍
    fun findByInventoryGreaterThanOrderByIdAsc(inventory: Int, pageable: Pageable): Page<Book>

    // 根据出版社分页查询书籍
    fun findByPress_IdAndInventoryGreaterThanOrderByIdAsc(id: Long, inventory: Int, pageable: Pageable): Page<Book>

    //  根据作者分页查询书籍
    fun findByAuthors_IdAndInventoryGreaterThanOrderByIdAsc(id: Long, inventory: Int, pageable: Pageable): Page<Book>

}
