package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.entity.*
import com.yuweihung.bookstore.repository.*
import mu.KotlinLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.YearMonth

private val logger = KotlinLogging.logger { }

/**
 * 测试用服务类
 */
@Transactional
@Service
class TestService(
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val bookRepository: BookRepository,
    val authorRepository: AuthorRepository,
    val pressRepository: PressRepository,
    val cartRepository: CartRepository,
    val orderRepository: OrderRepository,
    val encoder: PasswordEncoder,
) {
    /**
     * 初始化数据库信息
     */
    fun initDB() {
        val adminRole = Role("ADMIN")
        val userRole = Role("USER")
        roleRepository.save(adminRole)
        roleRepository.save(userRole)
        val encryptPassword = encoder.encode("123456")
        val adminRoleSet = mutableSetOf(userRole, adminRole)
        val userRoleSet = mutableSetOf(userRole)
        val adminCart = Cart()
        val userCart = Cart()
        cartRepository.save(adminCart)
        cartRepository.save(userCart)
        val admin = User("admin", encryptPassword, Gender.MALE, adminRoleSet, adminCart)
        val user = User("abc", encryptPassword, Gender.FEMALE, userRoleSet, userCart)
        userRepository.save(admin)
        userRepository.save(user)
        val author1 = Author("Randal E.Bryant")
        val author2 = Author("David O'Hallaron")
        val press1 = Press("机械工业出版社")
        authorRepository.save(author1)
        authorRepository.save(author2)
        pressRepository.save(press1)
        val authorSet1 = mutableSetOf(author1, author2)
        val book1 = Book(
            "深入理解计算机系统", "9787111544937", BigDecimal("139.00"), 737,
            YearMonth.of(2016, 11), 200, press1, authorSet1
        )
        bookRepository.save(book1)
        val author3 = Author("Bruce Eckel")
        authorRepository.save(author3)
        val authorSet2 = mutableSetOf(author3)
        val book2 = Book(
            "Java编程思想", "9787111213826", BigDecimal("108.00"), 880,
            YearMonth.of(2007, 6), 200, press1, authorSet2
        )
        bookRepository.save(book2)
        val author4 = Author("王爽")
        val press2 = Press("清华大学出版社")
        authorRepository.save(author4)
        pressRepository.save(press2)
        val authorSet3 = mutableSetOf(author4)
        val book3 = Book(
            "汇编语言", "9787302539414", BigDecimal("49.00"), 348,
            YearMonth.of(2020, 1), 200, press2, authorSet3
        )
        bookRepository.save(book3)
        val author5 = Author("Stanley B. Lippman")
        val author6 = Author("Josée Lajoie")
        val author7 = Author("Barbara E. Moo")
        val press3 = Press("电子工业出版社")
        authorRepository.save(author5)
        authorRepository.save(author6)
        authorRepository.save(author7)
        pressRepository.save(press3)
        val authorSet4 = mutableSetOf(author5, author6, author7)
        val book4 = Book(
            "C++ Primer", "9787121155352", BigDecimal("128.00"), 838,
            YearMonth.of(2013, 9), 200, press3, authorSet4
        )
        bookRepository.save(book4)
        logger.info { "Init the database" }
    }

}