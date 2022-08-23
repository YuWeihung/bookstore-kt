package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.dto.ChangePasswordDto
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@Rollback
internal class UserServiceTest(@Autowired val userService: UserService) {

    @Test
    fun changePassword() {
        val changePasswordDto = ChangePasswordDto("abc", "123456", "12345678")
        val user = userService.changePassword(changePasswordDto)
        val result = userService.encoder.matches("12345678", user.password)
        assertTrue(result)
    }
}