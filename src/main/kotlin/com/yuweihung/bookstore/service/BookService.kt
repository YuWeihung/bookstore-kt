package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.entity.Author
import com.yuweihung.bookstore.bean.entity.Book
import com.yuweihung.bookstore.bean.entity.Press
import com.yuweihung.bookstore.repository.AuthorRepository
import com.yuweihung.bookstore.repository.BookRepository
import com.yuweihung.bookstore.repository.PressRepository
import mu.KotlinLogging
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.YearMonth

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
    fun addBook(): Book {
        val press = Press("People Press")
        val author = Author("Tom")
        val book = Book(
            "Thinking in Java", "123-456-789", BigDecimal("19.99"),
            150, YearMonth.of(2020, 4), 100, press, author
        )
        pressRepository.save(press)
        authorRepository.save(author)
        bookRepository.save(book)
        return bookRepository.findByName("Thinking in Java")!!
    }

    @Cacheable("books")
    fun getName(name: String): String {
        logger.info { "RUN GET NAME $name" }
        return "The name is $name"
    }
}
