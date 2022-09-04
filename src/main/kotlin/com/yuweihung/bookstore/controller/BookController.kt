package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.common.Response
import com.yuweihung.bookstore.service.BookService
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 首页和搜索相关控制类
 */
@RestController
class BookController(
    val bookService: BookService,
) {
    /**
     * 分页获取书籍
     */
    @Cacheable("books")
    @GetMapping("/index")
    fun index(@RequestParam(value = "page", defaultValue = "1") page: Int): Response {
        val result = bookService.getBooks(page)
        return Response.success(result)
    }

    /**
     * 根据书名搜索书籍
     */
    @Cacheable("books")
    @GetMapping("/search/name")
    fun searchByName(name: String, @RequestParam(value = "page", defaultValue = "1") page: Int): Response {
        val result = bookService.searchByName(name, page)
        return Response.success(result)
    }

    /**
     * 根据出版社搜索书籍
     */
    @Cacheable("books")
    @GetMapping("/search/press")
    fun searchByPress(pressId: Long, @RequestParam(value = "page", defaultValue = "1") page: Int): Response {
        val result = bookService.searchByPress(pressId, page)
        return Response.success(result)
    }

    /**
     * 根据作者搜索书籍
     */
    @Cacheable("books")
    @GetMapping("/search/author")
    fun searchByAuthor(authorId: Long, @RequestParam(value = "page", defaultValue = "1") page: Int): Response {
        val result = bookService.searchByAuthor(authorId, page)
        return Response.success(result)
    }
}
