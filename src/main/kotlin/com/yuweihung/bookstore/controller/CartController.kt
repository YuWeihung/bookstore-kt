package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.dto.CartDto
import com.yuweihung.bookstore.bean.vo.CartVo
import com.yuweihung.bookstore.response.Response
import com.yuweihung.bookstore.service.CartService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * 购物车控制类
 */
@RestController
@RequestMapping("/cart")
class CartController(
    val cartService: CartService,
) {
    /**
     * 根据 id 查找购物车
     */
    @GetMapping("/{id}")
    fun getCart(@PathVariable("id") cartId: Long): Response {
        val cart = cartService.getCart(cartId)
        val result = CartVo(cart)
        return Response.success(result)
    }

    /**
     * 向购物车添加商品
     */
    @PostMapping("/add")
    fun addItem(@Valid @RequestBody cartDto: CartDto): Response {
        val cart = cartService.addItem(cartDto)
        val result = CartVo(cart)
        return Response.success(result)
    }

    /**
     * 从购物车移除商品
     */
    @PostMapping("/remove")
    fun removeItem(@Valid @RequestBody cartDto: CartDto): Response {
        val cart = cartService.removeItem(cartDto)
        val result = CartVo(cart)
        return Response.success(result)
    }

    /**
     * 修改购物车中商品数量
     */
    @PostMapping("/modify")
    fun modifyItem(@Valid @RequestBody cartDto: CartDto): Response {
        val cart = cartService.modifyItem(cartDto)
        val result = CartVo(cart)
        return Response.success(result)
    }
}
