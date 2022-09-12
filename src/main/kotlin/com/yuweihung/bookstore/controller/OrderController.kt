package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.dto.BuyBookDto
import com.yuweihung.bookstore.bean.dto.CheckoutDto
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
     * 直接购买商品
     */
    @PostMapping("/buy-book")
    fun buyBook(@Valid @RequestBody buyBookDto: BuyBookDto): Response {
        val result = orderService.buyBook(buyBookDto)
        return Response.success(result)
    }

    /**
     * 从购物车中提交订单
     */
    @PostMapping("/checkout-cart")
    fun checkoutCart(@Valid @RequestBody checkoutDto: CheckoutDto): Response {
        val result = orderService.checkoutCart(checkoutDto)
        return Response.success(result)
    }

    /**
     * 根据 id 获取订单详情
     */
    @GetMapping("/{id}")
    fun getOrder(@PathVariable("id") orderId: Long): Response {
        val result = orderService.getOrderDetail(orderId)
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
        val result = orderService.payOrder(orderId)
        return Response.success(result)
    }

    /**
     * 确认收货，完成订单
     */
    @PostMapping("/complete/{orderId}")
    fun completeOrder(@PathVariable("orderId") orderId: Long): Response {
        val result = orderService.completeOrder(orderId)
        return Response.success(result)
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancel/{orderId}")
    fun cancelOrder(@PathVariable("orderId") orderId: Long): Response {
        val result = orderService.cancelOrder(orderId)
        return Response.success(result)
    }
}
