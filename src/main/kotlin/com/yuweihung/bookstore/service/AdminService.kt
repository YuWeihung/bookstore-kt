package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.dto.BookDto
import com.yuweihung.bookstore.bean.dto.InventoryDto
import com.yuweihung.bookstore.bean.entity.Author
import com.yuweihung.bookstore.bean.entity.Book
import com.yuweihung.bookstore.bean.entity.Press
import com.yuweihung.bookstore.bean.entity.User
import com.yuweihung.bookstore.common.ErrorCode
import com.yuweihung.bookstore.common.ErrorException
import com.yuweihung.bookstore.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.YearMonth

/**
 * 管理员相关权限的服务类
 */
@Service
@Transactional
class AdminService(
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val bookRepository: BookRepository,
    val pressRepository: PressRepository,
    val authorRepository: AuthorRepository,
) {
    /**
     * 添加书籍
     */
    fun addBook(bookDto: BookDto): Book {
        val count = bookRepository.countByIsbn(bookDto.isbn)
        if (count != 0L) {
            throw ErrorException(ErrorCode.BOOK_ALREADY_EXIST)
        }
        var press = pressRepository.findByName(bookDto.press)
        if (press == null) {
            press = Press(bookDto.press)
            pressRepository.save(press)
        }
        val authorSet = mutableSetOf<Author>()
        for (name in bookDto.authors) {
            var author = authorRepository.findByName(name)
            if (author == null) {
                author = Author(name)
                authorRepository.save(author)
            }
            authorSet.add(author)
        }
        val price = BigDecimal(bookDto.price)
        val publishDate = YearMonth.of(bookDto.publishYear, bookDto.publishMonth)
        val book = Book(
            bookDto.name, bookDto.isbn, price, bookDto.pageCount,
            publishDate, bookDto.inventory, press, authorSet
        )
        return bookRepository.save(book)
    }

    /**
     * 修改库存
     */
    fun modifyInventory(inventoryDto: InventoryDto): Book {
        val book =
            bookRepository.findById(inventoryDto.bookId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        book.inventory = inventoryDto.inventory
        return bookRepository.save(book)
    }

    /**
     * 提升用户为管理员
     */
    fun elevatePrivilege(username: String): User {
        val user = userRepository.findByUsername(username) ?: throw ErrorException(ErrorCode.USER_NOT_EXIST)
        val role = roleRepository.findByName("ADMIN") ?: throw ErrorException(ErrorCode.ROLE_NOT_EXIST)
        if (user.roles.size == 1) {
            user.roles.add(role)
        } else {
            throw ErrorException(ErrorCode.ALREADY_ADMIN)
        }
        return userRepository.save(user)
    }
}
