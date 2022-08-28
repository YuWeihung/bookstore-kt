package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.dto.AddBookDto
import com.yuweihung.bookstore.bean.dto.InventoryDto
import com.yuweihung.bookstore.bean.vo.BookVo
import com.yuweihung.bookstore.bean.vo.UserVo
import com.yuweihung.bookstore.response.Response
import com.yuweihung.bookstore.service.AdminService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/admin")
class AdminController(
    val adminService: AdminService,
) {
    @PostMapping("/book/add")
    fun addBook(@Valid @RequestBody addBookDto: AddBookDto): Response {
        val book = adminService.addBook(addBookDto)
        val result = BookVo(book)
        return Response.success(result)
    }

    @PostMapping("/book/inventory")
    fun modifyInventory(@Valid @RequestBody inventoryDto: InventoryDto): Response {
        val book = adminService.modifyInventory(inventoryDto)
        val result = BookVo(book)
        return Response.success(result)
    }

    @PostMapping("/user/elevate/{username}")
    fun elevatePrivilege(@PathVariable("username") username: String): Response {
        val user = adminService.elevatePrivilege(username)
        val result = UserVo(user)
        return Response.success(result)
    }
}
