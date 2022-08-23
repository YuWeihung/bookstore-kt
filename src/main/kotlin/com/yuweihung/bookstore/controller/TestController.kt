package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.repository.RoleRepository
import com.yuweihung.bookstore.response.Response
import com.yuweihung.bookstore.service.BookService
import com.yuweihung.bookstore.service.UserService
import org.springframework.cache.annotation.Cacheable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.Min

/**
 * 测试用控制类
 */
@RestController
@Validated
class TestController(
    val userService: UserService,
    val bookService: BookService,
    val roleRepository: RoleRepository,
) {
    @GetMapping("/admin/hello")
    fun helloAdmin(): Response {
        return Response.success("Hello, admin")
    }

    @GetMapping("/hello")
    fun hello(): Response {
        return Response.success("Hello")
    }

    @Cacheable("books")
    @GetMapping("/test/1")
    fun test1(a: Int, b: Int): Response {
        val c = a + b
        return Response.success("$a + $b = $c")
    }

    @Cacheable
    @GetMapping("/test/2")
    fun test2(): Response {
        return Response.success("Test 2")
    }

    @Cacheable("cache")
    @GetMapping("/test")
    fun test(): Response {
        val result = roleRepository.findByName("ROLE_ADMIN")!!
        return Response.success(result)
    }

    @GetMapping("/test/{username}")
    fun test2(@Min(3, message = "还是太短了") @PathVariable("username") username: String): Response {
        return Response.success(username)
    }


}
