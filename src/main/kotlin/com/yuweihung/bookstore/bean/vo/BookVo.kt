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
        this.bookId = book.id
        this.name = book.name
        this.isbn = book.isbn
        this.price = book.price
        this.page = book.page
        this.publishYear = book.publishDate.year
        this.publishMonth = book.publishDate.monthValue
        this.inventory = book.inventory
        this.pressId = book.press.id
        this.pressName = book.press.name
        this.authors = book.authors.map { AuthorVo(it.id, it.name) }
    }
}
