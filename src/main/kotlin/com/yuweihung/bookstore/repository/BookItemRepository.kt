package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.BookItem
import org.springframework.data.jpa.repository.JpaRepository

interface BookItemRepository : JpaRepository<BookItem, Long> {
}
