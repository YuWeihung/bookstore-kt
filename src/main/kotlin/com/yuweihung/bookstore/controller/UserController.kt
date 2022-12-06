package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.dto.ChangePasswordDto
import com.yuweihung.bookstore.bean.dto.LoginDto
import com.yuweihung.bookstore.bean.dto.RefreshTokenDto
import com.yuweihung.bookstore.bean.dto.RegisterDto
import com.yuweihung.bookstore.common.Response
import com.yuweihung.bookstore.service.JwtService
import com.yuweihung.bookstore.service.UserService
import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

/**
 * 用户相关的控制类
 */
@RestController
class UserController(
    val userService: UserService,
    val jwtService: JwtService,
) {
    /**
     * 登录
     */
    @PostMapping("/login")
    fun login(@Valid @RequestBody loginDto: LoginDto): Response {
        val result = jwtService.login(loginDto)
        return Response.success(result)
    }

    /**
     * 刷新 Token
     */
    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody tokenDto: RefreshTokenDto): Response {
        val result = jwtService.refreshToken(tokenDto.accessToken)
        return Response.success(result)
    }

    /**
     * 注册账户
     */
    @PostMapping("/register")
    fun register(@Valid @RequestBody registerDto: RegisterDto): Response {
        val result = userService.register(registerDto)
        return Response.success(result)
    }

    /**
     * 修改密码
     */
    @PostMapping("/change-password")
    fun changePassword(@Valid @RequestBody changePasswordDto: ChangePasswordDto): Response {
        val authentication = SecurityContextHolder.getContext().authentication
        val result = userService.changePassword(changePasswordDto, authentication.name)
        return Response.success(result)
    }

    /**
     * 获取指定用户信息
     */
    @GetMapping("/user/{username}")
    fun getUser(@PathVariable("username") username: String): Response {
        val result = userService.getUser(username)
        return Response.success(result)
    }

}
