package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.entity.Book
import com.yuweihung.bookstore.repository.AuthorRepository
import com.yuweihung.bookstore.repository.BookRepository
import com.yuweihung.bookstore.repository.PressRepository
import com.yuweihung.bookstore.response.ErrorCode
import com.yuweihung.bookstore.response.ErrorException
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger { }

/**
 * 书籍相关的服务类
 */
@Transactional
@Service
class BookService(
    val bookRepository: BookRepository,
    val pressRepository: PressRepository,
    val authorRepository: AuthorRepository,
) {
    fun index(): List<Book> {
        val books = bookRepository.findAll()
        return books
    }

    fun searchByName(name: String): List<Book> {
        logger.info { "The name is $name" }
        val books = bookRepository.findByNameLike("%$name%")
        if (books.isEmpty()) {
            throw ErrorException(ErrorCode.RESULT_NOT_FOUND)
        } else {
            return books
        }
    }

    fun searchByPress(id: Long): List<Book> {
        val books = bookRepository.findByPress_Id(id)
        if (books.isEmpty()) {
            throw ErrorException(ErrorCode.RESULT_NOT_FOUND)
        } else {
            return books
        }
    }
}
