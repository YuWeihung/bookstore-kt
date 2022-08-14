package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.dto.ChangePasswordDto
import com.yuweihung.bookstore.bean.dto.UserDto
import com.yuweihung.bookstore.bean.entity.Role
import com.yuweihung.bookstore.bean.entity.User
import com.yuweihung.bookstore.repository.RoleRepository
import com.yuweihung.bookstore.repository.UserRepository
import com.yuweihung.bookstore.response.ErrorCode
import com.yuweihung.bookstore.response.ErrorException
import mu.KotlinLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger { }

/**
 * 账户相关的服务类
 * @author Yu Weihong
 * @since 2022/7/25
 */
@Transactional
@Service
class AccountService(
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val encoder: PasswordEncoder,
) {

    /**
     * 初始化数据库信息
     */
    fun initDB() {
        val adminRole = Role("ROLE_ADMIN")
        val userRole = Role("ROLE_USER")
        roleRepository.save(adminRole)
        roleRepository.save(userRole)
        val encryptPassword = encoder.encode("admin")
        val adminRoleSet = mutableSetOf(userRole, adminRole)
        val admin = User("admin", encryptPassword, 0, adminRoleSet)
        userRepository.save(admin)
        logger.info { "Init the database" }
    }

    /**
     * 用户注册
     */
    fun register(userDto: UserDto): User {
        val count = userRepository.countByUsername(userDto.username)
        if (count > 0) {
            throw ErrorException(ErrorCode.USER_ALREADY_EXIST)
        }
        val encryptPassword = encoder.encode(userDto.password)
        val role = roleRepository.findByName("ROLE_USER")
        val roles = if (role == null) {
            throw ErrorException(ErrorCode.ROLE_NOT_EXIST)
        } else {
            mutableSetOf(role)
        }
        val user = User(userDto.username, encryptPassword, userDto.gender, roles)
        return userRepository.save(user)
    }

    /**
     * 更改密码
     */
    fun changePassword(changePasswordDto: ChangePasswordDto): User {
        val user = userRepository.findByUsername(changePasswordDto.username)
        if (user == null) {
            throw ErrorException(ErrorCode.USER_NOT_EXIST)
        } else if (!encoder.matches(changePasswordDto.oldPassword, user.password)) {
            throw ErrorException(ErrorCode.PASSWORD_WRONG)
        } else {
            val encryptPassword = encoder.encode(changePasswordDto.newPassword)
            user.password = encryptPassword
            return userRepository.save(user)
        }
    }

}
