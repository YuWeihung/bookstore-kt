package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.vo.CartVo
import com.yuweihung.bookstore.bean.vo.OrderVo
import com.yuweihung.bookstore.response.Response
import com.yuweihung.bookstore.service.*
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 测试用控制类
 */
@RestController
@Validated
class TestController(
    val userService: UserService,
    val bookService: BookService,
    val cartService: CartService,
    val orderService: OrderService,
    val testService: TestService,
) {
    @GetMapping("/test/init")
    fun initRole(): Response {
        testService.initDB()
        return Response.success("init database")
    }

    @GetMapping("/admin/hello")
    fun helloAdmin(): Response {
        return Response.success("Hello, admin")
    }

    @GetMapping("/hello")
    fun hello(): Response {
        return Response.success("Hello")
    }

    @GetMapping("/test/1")
    fun test1(bookId: Long, amount: Int): Response {
        val cart = cartService.addItem(2, bookId, amount)
        val result = CartVo(cart)
        return Response.success(result)
    }

    @GetMapping("/test/2")
    fun test2(bookId: Long): Response {
        val cart = cartService.removeItem(2, bookId)
        val result = CartVo(cart)
        return Response.success(result)
    }

    @GetMapping("/test/3")
    fun test3(bookId: Long, amount: Int): Response {
        val cart = cartService.modifyItem(2, bookId, amount)
        val result = CartVo(cart)
        return Response.success(result)
    }

    @GetMapping("/test/4")
    fun test4(): Response {
        val order = orderService.submitOrder(2, 2)
        val result = OrderVo(order)
        return Response.success(result)
    }

//    @GetMapping("/test/{username}")
//    fun test2(@Min(3, message = "还是太短了") @PathVariable("username") username: String): Response {
//        return Response.success(username)
//    }

}
