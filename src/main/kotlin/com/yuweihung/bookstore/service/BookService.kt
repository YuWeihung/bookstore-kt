package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.entity.Book
import com.yuweihung.bookstore.repository.BookRepository
import com.yuweihung.bookstore.response.ErrorCode
import com.yuweihung.bookstore.response.ErrorException
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
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
) {
    companion object {
        const val PAGE_SIZE = 10
    }

    /**
     * 分页获取全部书籍
     */
    fun getBooks(page: Int = 0): List<Book> {
        val pageable = PageRequest.of(page, PAGE_SIZE)
        val books = bookRepository.findByInventoryGreaterThan(0, pageable)
        if (books.isEmpty) {
            throw ErrorException(ErrorCode.NO_SEARCH_RESULT)
        } else {
            return books.content
        }
    }

    /**
     * 根据标题模糊搜索
     */
    fun searchByName(name: String, page: Int = 0): List<Book> {
        val pageable = PageRequest.of(page, PAGE_SIZE)
        val books = bookRepository.findByNameLikeAndInventoryGreaterThan("%$name%", 0, pageable)
        if (books.isEmpty) {
            throw ErrorException(ErrorCode.NO_SEARCH_RESULT)
        } else {
            return books.content
        }
    }

    /**
     * 根据出版社搜索
     */
    fun searchByPress(pressId: Long, page: Int = 0): List<Book> {
        val pageable = PageRequest.of(page, PAGE_SIZE)
        val books = bookRepository.findByPress_IdAndInventoryGreaterThan(pressId, 0, pageable)
        if (books.isEmpty) {
            throw ErrorException(ErrorCode.NO_SEARCH_RESULT)
        } else {
            return books.content
        }
    }

    /**
     * 根据作者搜索
     */
    fun searchByAuthor(authorId: Long, page: Int = 0): List<Book> {
        val pageable = PageRequest.of(page, PAGE_SIZE)
        val books = bookRepository.findByAuthors_IdAndInventoryGreaterThan(authorId, 0, pageable)
        if (books.isEmpty) {
            throw ErrorException(ErrorCode.NO_SEARCH_RESULT)
        } else {
            return books.content
        }
    }

}
