package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.dto.ChangePasswordDto
import com.yuweihung.bookstore.bean.dto.UserDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@Rollback
internal class AccountServiceTest(@Autowired val accountService: AccountService) {

    @Test
    fun register() {
        val userDto = UserDto("def", "123456")
        val result = accountService.register(userDto)
        assertEquals("def", result.username)
    }

    @Test
    fun changePassword() {
        val changePasswordDto = ChangePasswordDto("abc", "123456", "12345678")
        val user = accountService.changePassword(changePasswordDto)
        val result = accountService.encoder.matches("12345678", user.password)
        assertTrue(result)
    }
}