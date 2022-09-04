package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.dto.OrderDto
import com.yuweihung.bookstore.bean.vo.OrderVo
import com.yuweihung.bookstore.common.Response
import com.yuweihung.bookstore.service.OrderService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

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
    @PostMapping("/submit")
    fun submitOrder(@Valid @RequestBody orderDto: OrderDto): Response {
        val order = orderService.submitOrder(orderDto)
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
    fun getOrdersByUser(username: String, @RequestParam(value = "page", defaultValue = "1") page: Int): Response {
        val result = orderService.getOrdersByUser(username, page)
        return Response.success(result)
    }

    /**
     * 支付订单
     */
    @PostMapping("/pay/{orderId}")
    fun payOrder(@PathVariable("orderId") orderId: Long): Response {
        val order = orderService.payOrder(orderId)
        val result = OrderVo(order)
        return Response.success(result)
    }

    /**
     * 确认收货，完成订单
     */
    @PostMapping("/complete/{orderId}")
    fun completeOrder(@PathVariable("orderId") orderId: Long): Response {
        val order = orderService.completeOrder(orderId)
        val result = OrderVo(order)
        return Response.success(result)
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancel/{orderId}")
    fun cancelOrder(@PathVariable("orderId") orderId: Long): Response {
        val order = orderService.cancelOrder(orderId)
        val result = OrderVo(order)
        return Response.success(result)
    }
}
