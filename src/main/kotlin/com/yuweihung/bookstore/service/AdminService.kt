package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.dto.BookDto
import com.yuweihung.bookstore.bean.dto.InventoryDto
import com.yuweihung.bookstore.bean.entity.Author
import com.yuweihung.bookstore.bean.entity.Book
import com.yuweihung.bookstore.bean.entity.Press
import com.yuweihung.bookstore.bean.vo.BookVo
import com.yuweihung.bookstore.bean.vo.UserVo
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
    fun addBook(bookDto: BookDto): BookVo {
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
            bookDto.name, bookDto.isbn, price, bookDto.page,
            publishDate, bookDto.inventory, press, authorSet
        )
        val result = bookRepository.save(book)
        return BookVo(result)
    }

    /**
     * 修改库存
     */
    fun modifyInventory(inventoryDto: InventoryDto): BookVo {
        val book =
            bookRepository.findById(inventoryDto.bookId).orElse(null) ?: throw ErrorException(ErrorCode.NO_SUCH_ITEM)
        book.inventory = inventoryDto.inventory
        val result = bookRepository.save(book)
        return BookVo(result)
    }

    /**
     * 提升用户为管理员
     */
    fun elevatePrivilege(username: String): UserVo {
        val user = userRepository.findByUsername(username) ?: throw ErrorException(ErrorCode.USER_NOT_EXIST)
        val role = roleRepository.findByName("ADMIN") ?: throw ErrorException(ErrorCode.ROLE_NOT_EXIST)
        if (user.roles.size == 1) {
            user.roles.add(role)
        } else {
            throw ErrorException(ErrorCode.ALREADY_ADMIN)
        }
        val result = userRepository.save(user)
        return UserVo(result)
    }
}
