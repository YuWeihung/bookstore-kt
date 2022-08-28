package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.vo.OrderVo
import com.yuweihung.bookstore.response.Response
import com.yuweihung.bookstore.service.OrderService
import org.springframework.web.bind.annotation.*

/**
 * 订单控制类
 */
@RestController
@RequestMapping("/order")
class OrderController(
    val orderService: OrderService,
) {
    /**
     * 提交订单
     */
    @PostMapping("/submit/{username}")
    fun submitOrder(@PathVariable("username") username: String): Response {
        val order = orderService.submitOrder(username)
        val result = OrderVo(order)
        return Response.success(result)
    }

    /**
     * 根据 id 获取订单详情
     */
    @GetMapping("/{id}")
    fun getOrder(@PathVariable("id") orderId: Long): Response {
        val order = orderService.getOrderDetail(orderId)
        val result = OrderVo(order)
        return Response.success(result)
    }

    /**
     * 分页获取用户订单
     */
    @GetMapping("/user")
    fun getOrdersByUser(username: String, page: Int): Response {
        val order = orderService.getOrdersByUser(username, page)
        val result = order.map { OrderVo(it) }
        return Response.success(result)
    }
}
