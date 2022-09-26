package com.yuweihung.bookstore.bean.vo

import com.yuweihung.bookstore.bean.entity.Book
import java.math.BigDecimal

/**
 * 书籍信息
 */
class BookVo() {
    var bookId: Long? = null
    var name: String = ""
    var isbn: String = ""
    var price: BigDecimal = BigDecimal("0.00")
    var page: Int = 0
    var publishYear: Int = 0
    var publishMonth: Int = 0
    var inventory: Int = 0
    var pressId: Long? = null
    var pressName: String = ""
    var authors: List<AuthorVo> = listOf()

    constructor(book: Book) : this() {
        bookId = book.id
        name = book.name
        isbn = book.isbn
        price = book.price
        page = book.page
        publishYear = book.publishDate.year
        publishMonth = book.publishDate.monthValue
        inventory = book.inventory
        pressId = book.press.id
        pressName = book.press.name
        authors = book.authors.map { AuthorVo(it.id, it.name) }
    }
}
