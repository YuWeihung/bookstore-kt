package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.dto.AddBookDto
import com.yuweihung.bookstore.bean.dto.InventoryDto
import com.yuweihung.bookstore.bean.vo.BookVo
import com.yuweihung.bookstore.response.Response
import com.yuweihung.bookstore.service.AdminService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController(
    val adminService: AdminService,
) {
    @PostMapping("/book/add")
    fun addBook(addBookDto: AddBookDto): Response {
        val book = adminService.addBook(addBookDto)
        val result = BookVo(book)
        return Response.success(result)
    }

    @PostMapping("/book/inventory")
    fun modifyInventory(inventoryDto: InventoryDto): Response {
        val book = adminService.modifyInventory(inventoryDto.bookId, inventoryDto.inventory)
        val result = BookVo(book)
        return Response.success(result)
    }
}