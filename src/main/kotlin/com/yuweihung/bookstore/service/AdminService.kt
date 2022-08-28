package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.dto.AddBookDto
import com.yuweihung.bookstore.bean.entity.Author
import com.yuweihung.bookstore.bean.entity.Book
import com.yuweihung.bookstore.bean.entity.Press
import com.yuweihung.bookstore.repository.AuthorRepository
import com.yuweihung.bookstore.repository.BookRepository
import com.yuweihung.bookstore.repository.PressRepository
import com.yuweihung.bookstore.repository.UserRepository
import com.yuweihung.bookstore.response.ErrorCode
import com.yuweihung.bookstore.response.ErrorException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.YearMonth

@Service
@Transactional
class AdminService(
    val userRepository: UserRepository,
    val bookRepository: BookRepository,
    val pressRepository: PressRepository,
    val authorRepository: AuthorRepository,
) {
    /**
     * 添加书籍
     */
    fun addBook(addBookDto: AddBookDto): Book {
        var press = pressRepository.findByName(addBookDto.press)
        if (press == null) {
            press = Press(addBookDto.press)
            pressRepository.save(press)
        }
        val authorSet = mutableSetOf<Author>()
        for (name in addBookDto.authors) {
            var author = authorRepository.findByName(name)
            if (author == null) {
                author = Author(name)
                authorRepository.save(author)
            }
            authorSet.add(author)
        }
        val price = BigDecimal(addBookDto.price)
        val publishDate = YearMonth.of(addBookDto.publishYear, addBookDto.publishMonth)
        val book = Book(
            addBookDto.name, addBookDto.isbn, price, addBookDto.pageCount,
            publishDate, addBookDto.inventory, press, authorSet
        )
        return bookRepository.save(book)
    }

    /**
     * 修改库存
     */
    fun modifyInventory(bookId: Long, inventory: Int): Book {
        val book = bookRepository.findById(bookId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        book.inventory = inventory
        return bookRepository.save(book)
    }
}
