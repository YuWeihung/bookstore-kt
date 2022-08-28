package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.dto.BookDto
import com.yuweihung.bookstore.bean.entity.Cart
import com.yuweihung.bookstore.bean.entity.Gender
import com.yuweihung.bookstore.bean.entity.Role
import com.yuweihung.bookstore.bean.entity.User
import com.yuweihung.bookstore.repository.CartRepository
import com.yuweihung.bookstore.repository.RoleRepository
import com.yuweihung.bookstore.repository.UserRepository
import mu.KotlinLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger { }

/**
 * 测试用服务类
 */
@Transactional
@Service
class TestService(
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val cartRepository: CartRepository,
    val adminService: AdminService,
    val encoder: PasswordEncoder,
) {
    /**
     * 初始化数据库信息
     */
    fun initDB() {
        initUsers()
        initBooks()
    }

    /**
     * 初始化用户和管理员
     */
    fun initUsers() {
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
        logger.info { "init the users" }
    }

    /**
     * 初始化书籍
     */
    fun initBooks() {
        val book1 = BookDto(
            "深入理解计算机系统", "9787111544937", "139.00", 737,
            2016, 11, 200, "机械工业出版社",
            listOf("Randal E.Bryant", "David O'Hallaron")
        )
        adminService.addBook(book1)
        val book2 = BookDto(
            "计算机程序的构造和解释", "9787111135104", "45.00", 473,
            2004, 2, 200, "机械工业出版社",
            listOf("Harold Abelson", "Gerald Jay Sussman", "Julie Sussman")
        )
        adminService.addBook(book2)
        val book3 = BookDto(
            "UNIX环境高级编程", "9787115352118", "128.00", 812,
            2014, 6, 200, "人民邮电出版社",
            listOf("W.Richard Stevens", "Stephen A.Rago")
        )
        adminService.addBook(book3)
        val book4 = BookDto(
            "UNIX网络编程", "9787115367198", "129.00", 824,
            2014, 6, 200, "人民邮电出版社",
            listOf("W.Richard Stevens", "Bill Fenner", "Andrew M.Rudoff")
        )
        adminService.addBook(book4)
        val book5 = BookDto(
            "C++ Primer", "9787121155352", "128.00", 838,
            2013, 9, 200, "电子工业出版社",
            listOf("Stanley B.Lippman", "Josée Lajoie", "Barbara E.Moo")
        )
        adminService.addBook(book5)
        val book6 = BookDto(
            "算法", "9787115293800", "99.00", 636,
            2012, 10, 200, "人民邮电出版社",
            listOf("Robert Sedgewick", "Kevin Wayne")
        )
        adminService.addBook(book6)
        val book7 = BookDto(
            "编译原理", "9787111251217", "89.00", 631,
            2008, 12, 200, "机械工业出版社",
            listOf("Alfred V.Aho", "Monica S.Lam", "Ravi Sethi", "Jeffrey D.Ullman")
        )
        adminService.addBook(book7)
        val book8 = BookDto(
            "汇编语言", "9787302539414", "49.00", 348,
            2020, 1, 200, "清华大学出版社",
            listOf("王爽")
        )
        adminService.addBook(book8)
        val book9 = BookDto(
            "软件工程", "9787111335818", "79.00", 641,
            2011, 1, 200, "机械工业出版社",
            listOf("Roger S.Pressman")
        )
        adminService.addBook(book9)
        val book10 = BookDto(
            "Java编程思想", "9787111213826", "108.00", 880,
            2007, 6, 200, "机械工业出版社",
            listOf("Bruce Eckel")
        )
        adminService.addBook(book10)
        val book11 = BookDto(
            "计算机网络", "9787111599715", "89.00", 480,
            2018, 6, 200, "机械工业出版社",
            listOf("James F.Kurose", "Keith W.Ross")
        )
        adminService.addBook(book11)
        val book12 = BookDto(
            "算法导论", "9787111407010", "128.00", 780,
            2012, 12, 200, "机械工业出版社",
            listOf("Thomas H.Cormen", "Charles E.Leiserson", "Ronald L.Rivest", "Clifford Stein")
        )
        adminService.addBook(book12)
        val book13 = BookDto(
            "现代操作系统", "9787111573692", "89.00", 610,
            2017, 7, 200, "机械工业出版社",
            listOf("Andrew S.Tanenbaum", "Herbert Bos")
        )
        adminService.addBook(book13)
        val book14 = BookDto(
            "人月神话", "9787302155676", "48.00", 315,
            2007, 9, 200, "清华大学出版社",
            listOf("Fred Brooks")
        )
        adminService.addBook(book14)
        val book15 = BookDto(
            "编码", "9787121181184", "59.00", 420,
            2012, 10, 200, "电子工业出版社",
            listOf("Charles Petzold")
        )
        adminService.addBook(book15)
        val book16 = BookDto(
            "LaTeX入门", "9787121202087", "79.00", 566,
            2013, 6, 200, "电子工业出版社",
            listOf("刘海洋")
        )
        adminService.addBook(book16)
        val book17 = BookDto(
            "JavaScript高级程序设计", "9787115545381", "129.00", 888,
            2020, 8, 200, "人民邮电出版社",
            listOf("Matt Frisbie")
        )
        adminService.addBook(book17)
        logger.info { "init the books" }
    }
}