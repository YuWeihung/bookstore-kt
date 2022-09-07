package com.yuweihung.bookstore.service

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
    }
}