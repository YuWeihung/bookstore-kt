package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.vo.BookListVo
import com.yuweihung.bookstore.common.Constant
import com.yuweihung.bookstore.common.ErrorCode
import com.yuweihung.bookstore.common.ErrorException
import com.yuweihung.bookstore.repository.BookRepository
import mu.KotlinLogging
import org.springframework.cache.annotation.Cacheable
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

    /**
     * 分页获取全部书籍
     */
    @Cacheable("books")
    fun getBooks(page: Int): BookListVo {
        val pageable = PageRequest.of(page - 1, Constant.PAGE_SIZE)
        val books = bookRepository.findByInventoryGreaterThanOrderByIdAsc(0, pageable)
        if (books.isEmpty) {
            throw ErrorException(ErrorCode.NO_SEARCH_RESULT)
        } else {
            return BookListVo(books, page)
        }
    }

    /**
     * 根据标题模糊搜索
     */
    @Cacheable("books")
    fun searchByName(name: String, page: Int): BookListVo {
        val pageable = PageRequest.of(page - 1, Constant.PAGE_SIZE)
        val books = bookRepository.findByNameLikeAndInventoryGreaterThanOrderByIdAsc("%$name%", 0, pageable)
        if (books.isEmpty) {
            throw ErrorException(ErrorCode.NO_SEARCH_RESULT)
        } else {
            return BookListVo(books, page)
        }
    }

    /**
     * 根据出版社搜索
     */
    @Cacheable("books")
    fun searchByPress(pressId: Long, page: Int): BookListVo {
        val pageable = PageRequest.of(page - 1, Constant.PAGE_SIZE)
        val books = bookRepository.findByPress_IdAndInventoryGreaterThanOrderByIdAsc(pressId, 0, pageable)
        if (books.isEmpty) {
            throw ErrorException(ErrorCode.NO_SEARCH_RESULT)
        } else {
            return BookListVo(books, page)
        }
    }

    /**
     * 根据作者搜索
     */
    @Cacheable("books")
    fun searchByAuthor(authorId: Long, page: Int): BookListVo {
        val pageable = PageRequest.of(page - 1, Constant.PAGE_SIZE)
        val books = bookRepository.findByAuthors_IdAndInventoryGreaterThanOrderByIdAsc(authorId, 0, pageable)
        if (books.isEmpty) {
            throw ErrorException(ErrorCode.NO_SEARCH_RESULT)
        } else {
            return BookListVo(books, page)
        }
    }

}
