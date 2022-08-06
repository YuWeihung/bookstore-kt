package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.response.Response
import com.yuweihung.bookstore.service.AccountService
import com.yuweihung.bookstore.service.BookService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.Min

/**
 * 测试用控制类
 * @author Yu Weihong
 * @since 2022/7/30
 */
@RestController
@Validated
class TestController(
    val accountService: AccountService,
    val bookService: BookService,
) {
    @GetMapping("/admin/hello")
    fun helloAdmin(): Response {
        return Response.success("Hello, admin")
    }

    @GetMapping("/hello")
    fun hello(): Response {
        return Response.success("Hello")
    }

    @GetMapping("/test")
    fun getUser(): Response {
        val result = bookService.addBook("C++ primer")
        return Response.success(result)
    }

    @GetMapping("/test/{username}")
    fun test2(@Min(3, message = "还是太短了") @PathVariable("username") username: String): Response {
        return Response.success(username)
    }

    @GetMapping("/test/init")
    fun initRole(): Response {
        accountService.initDB()
        return Response.success("init database")
    }
}
