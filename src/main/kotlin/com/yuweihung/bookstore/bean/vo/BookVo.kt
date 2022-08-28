package com.yuweihung.bookstore.bean.vo

import com.yuweihung.bookstore.bean.entity.Book
import java.math.BigDecimal
import java.time.YearMonth

class BookVo(book: Book) {
    var id: Long?
    var name: String
    var isbn: String
    var price: BigDecimal
    var pageCount: Int
    var publishDate: YearMonth
    var inventory: Int
    var pressId: Long?
    var pressName: String
    var authors: List<AuthorVo>

    init {
        id = book.id
        name = book.name
        isbn = book.isbn
        price = book.price
        pageCount = book.pageCount
        publishDate = book.publishDate
        inventory = book.inventory
        pressId = book.press.id
        pressName = book.press.name
        authors = book.authors.map { AuthorVo(it.id, it.name) }
    }
}
