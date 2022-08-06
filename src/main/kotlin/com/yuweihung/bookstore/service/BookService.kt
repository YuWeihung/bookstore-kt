package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.entity.Book
import com.yuweihung.bookstore.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 书籍相关的服务类
 * @author Yu Weihong
 * @since 2022/7/25
 */
@Transactional
@Service
class BookService(
    val bookRepository: BookRepository,
) {
    fun addBook(name: String): Book {
        return bookRepository.save(Book(name))
    }

}
