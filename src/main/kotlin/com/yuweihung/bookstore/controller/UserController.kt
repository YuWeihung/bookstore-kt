package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.dto.ChangePasswordDto
import com.yuweihung.bookstore.bean.dto.RegisterDto
import com.yuweihung.bookstore.bean.vo.UserVo
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
    /**
     * 注册账户
     */
    @PostMapping("/register")
    fun register(@Valid @RequestBody registerDto: RegisterDto): Response {
        val user = userService.register(registerDto)
        val result = UserVo(user)
        return Response.success(result)
    }

    /**
     * 修改密码
     */
    @PostMapping("/change-password")
    fun changePassword(@Valid @RequestBody changePasswordDto: ChangePasswordDto): Response {
        val user = userService.changePassword(changePasswordDto)
        val result = UserVo(user)
        return Response.success(result)
    }

    /**
     * 获取指定用户信息
     */
    @GetMapping("/user/{username}")
    fun getUser(@PathVariable("username") username: String): Response {
        val user = userService.getUser(username)
        val result = UserVo(user)
        return Response.success(result)
    }

}
