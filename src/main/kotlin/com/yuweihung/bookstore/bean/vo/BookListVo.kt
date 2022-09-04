package com.yuweihung.bookstore.bean.vo

import com.yuweihung.bookstore.bean.entity.Book
import org.springframework.data.domain.Page

/**
 * 书籍分页查询结果
 */
class BookListVo() {
    var totalPage: Int = 0
    var page: Int = 0
    var books: List<BookVo> = listOf()

    constructor(books: Page<Book>, page: Int) : this() {
        this.totalPage = books.totalPages
        this.books = books.content.map { BookVo(it) }
        this.page = page
    }
}
