package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.vo.OrderVo
import com.yuweihung.bookstore.response.Response
import com.yuweihung.bookstore.service.OrderService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order")
class OrderController(
    val orderService: OrderService,
) {
    @PostMapping("/submit/{username}")
    fun submitOrder(@PathVariable("username") username: String): Response {
        val order = orderService.submitOrder(username)
        val result = OrderVo(order)
        return Response.success(result)
    }

    @GetMapping("/{id}")
    fun getOrder(@PathVariable("id") orderId: Long): Response {
        val order = orderService.getOrderDetail(orderId)
        val result = OrderVo(order)
        return Response.success(result)
    }

    @GetMapping("/user")
    fun getOrdersByUser(username: String): Response {
        val order = orderService.getOrdersByUser(username)
        val result = order.map { OrderVo(it) }
        return Response.success(result)
    }
}
