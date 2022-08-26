package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.vo.BookVo
import com.yuweihung.bookstore.response.Response
import com.yuweihung.bookstore.service.BookService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger { }

@RestController
class IndexController(
    val bookService: BookService,
) {
    @GetMapping("/index")
    fun index(): Response {
        val books = bookService.index()
        val result = books.map { BookVo(it) }
        return Response.success(result)
    }

    @GetMapping("/search/name")
    fun searchByName(name: String): Response {
        val books = bookService.searchByName(name)
        val result = books.map { BookVo(it) }
        return Response.success(result)
    }

    @GetMapping("/search/press")
    fun searchByPress(id: Long): Response {
        val books = bookService.searchByPress(id)
        val result = books.map { BookVo(it) }
        return Response.success(result)
    }
}