package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.dto.BookDto
import com.yuweihung.bookstore.bean.dto.InventoryDto
import com.yuweihung.bookstore.bean.vo.BookVo
import com.yuweihung.bookstore.bean.vo.UserVo
import com.yuweihung.bookstore.response.Response
import com.yuweihung.bookstore.service.AdminService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * 管理员控制类
 */
@RestController
@RequestMapping("/admin")
class AdminController(
    val adminService: AdminService,
) {
    /**
     * 添加书籍
     */
    @PostMapping("/book/add")
    fun addBook(@Valid @RequestBody addBookDto: BookDto): Response {
        val book = adminService.addBook(addBookDto)
        val result = BookVo(book)
        return Response.success(result)
    }

    /**
     * 增加库存
     */
    @PostMapping("/book/inventory")
    fun modifyInventory(@Valid @RequestBody inventoryDto: InventoryDto): Response {
        val book = adminService.modifyInventory(inventoryDto)
        val result = BookVo(book)
        return Response.success(result)
    }

    /**
     * 提升用户权限为管理员
     */
    @PostMapping("/user/elevate/{username}")
    fun elevatePrivilege(@PathVariable("username") username: String): Response {
        val user = adminService.elevatePrivilege(username)
        val result = UserVo(user)
        return Response.success(result)
    }
}
