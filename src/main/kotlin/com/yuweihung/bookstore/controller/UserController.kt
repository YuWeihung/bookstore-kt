package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.dto.ChangePasswordDto
import com.yuweihung.bookstore.bean.dto.UserDto
import com.yuweihung.bookstore.response.Response
import com.yuweihung.bookstore.service.UserService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * 用户相关的控制类
 */
@RestController
class UserController(
    val userService: UserService,
) {
    @GetMapping("/test/init")
    fun initRole(): Response {
        userService.initDB()
        return Response.success("init database")
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody userDto: UserDto): Response {
        val result = userService.register(userDto)
        return Response.success(result)
    }

    @PostMapping("/change-password")
    fun changePassword(@Valid @RequestBody changePasswordDto: ChangePasswordDto): Response {
        val result = userService.changePassword(changePasswordDto)
        return Response.success(result)
    }

    @GetMapping("/user/{username}")
    fun getUser(@PathVariable("username") username: String): Response {
        val result = userService.getUser(username)
        return Response.success(result)
    }

}
