package com.yuweihung.bookstore.controller

import com.yuweihung.bookstore.bean.dto.ChangePasswordDto
import com.yuweihung.bookstore.bean.dto.UserDto
import com.yuweihung.bookstore.response.Response
import com.yuweihung.bookstore.service.AccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * 账户相关的控制类
 * @author Yu Weihong
 * @since 2022/7/30
 */
@RestController
class AccountController(
    val accountService: AccountService,
) {
    @GetMapping("/test/init")
    fun initRole(): Response {
        accountService.initDB()
        return Response.success("init database")
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody userDto: UserDto): Response {
        val result = accountService.register(userDto)
        return Response.success(result)
    }

    @PostMapping("/change-password")
    fun changePassword(@Valid @RequestBody changePasswordDto: ChangePasswordDto): Response {
        val result = accountService.changePassword(changePasswordDto)
        return Response.success(result)
    }

}
