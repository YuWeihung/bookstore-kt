package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.dto.ChangePasswordDto
import com.yuweihung.bookstore.bean.dto.UserDto
import com.yuweihung.bookstore.bean.vo.UserVo
import com.yuweihung.bookstore.response.Response
import com.yuweihung.bookstore.service.TestService
import com.yuweihung.bookstore.service.UserService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * 用户相关的控制类
 */
@RestController
class UserController(
    val userService: UserService,
    val testService: TestService,
) {
    @GetMapping("/test/init")
    fun initRole(): Response {
        testService.initDB()
        return Response.success("init database")
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody userDto: UserDto): Response {
        val user = userService.register(userDto)
        val result = UserVo(user)
        return Response.success(result)
    }

    @PostMapping("/change-password")
    fun changePassword(@Valid @RequestBody changePasswordDto: ChangePasswordDto): Response {
        val user = userService.changePassword(changePasswordDto)
        val result = UserVo(user)
        return Response.success(result)
    }

    @GetMapping("/user/{username}")
    fun getUser(@PathVariable("username") username: String): Response {
        val user = userService.getUser(username)
        val result = UserVo(user)
        return Response.success(result)
    }

}
