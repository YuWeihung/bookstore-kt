package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.dto.ChangePasswordDto
import com.yuweihung.bookstore.bean.dto.UserDto
import com.yuweihung.bookstore.bean.entity.Gender
import com.yuweihung.bookstore.bean.entity.Role
import com.yuweihung.bookstore.bean.entity.User
import com.yuweihung.bookstore.bean.vo.UserVo
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
 * 用户相关的服务类
 */
@Transactional
@Service
class UserService(
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
        val admin = User("admin", encryptPassword, Gender.MALE, adminRoleSet)
        userRepository.save(admin)
        logger.info { "Init the database" }
    }

    /**
     * 用户注册
     */
    fun register(userDto: UserDto): UserVo {
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
        val gender = if (userDto.gender == "female") Gender.FEMALE else Gender.MALE
        val user = User(userDto.username, encryptPassword, gender, roles)
        val result = userRepository.save(user)
        return UserVo(result)
    }

    /**
     * 更改密码
     */
    fun changePassword(changePasswordDto: ChangePasswordDto): UserVo {
        val user = userRepository.findByUsername(changePasswordDto.username)
        if (user == null) {
            throw ErrorException(ErrorCode.USER_NOT_EXIST)
        } else if (!encoder.matches(changePasswordDto.oldPassword, user.password)) {
            throw ErrorException(ErrorCode.PASSWORD_WRONG)
        } else {
            val encryptPassword = encoder.encode(changePasswordDto.newPassword)
            user.password = encryptPassword
            userRepository.save(user)
            return UserVo(user)
        }
    }

    /**
     * 获取指定用户信息
     */
    fun getUser(username: String): UserVo {
        val user = userRepository.findByUsername(username)
        return user?.let { UserVo(it) } ?: throw ErrorException(ErrorCode.USER_NOT_EXIST)
    }

}
