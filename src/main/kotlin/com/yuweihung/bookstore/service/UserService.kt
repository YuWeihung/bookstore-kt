package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.dto.ChangePasswordDto
import com.yuweihung.bookstore.bean.dto.RegisterDto
import com.yuweihung.bookstore.bean.entity.Cart
import com.yuweihung.bookstore.bean.entity.Gender
import com.yuweihung.bookstore.bean.entity.User
import com.yuweihung.bookstore.repository.CartRepository
import com.yuweihung.bookstore.repository.RoleRepository
import com.yuweihung.bookstore.repository.UserRepository
import com.yuweihung.bookstore.response.ErrorCode
import com.yuweihung.bookstore.response.ErrorException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 用户相关的服务类
 */
@Transactional
@Service
class UserService(
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val cartRepository: CartRepository,
    val encoder: PasswordEncoder,
) {
    /**
     * 用户注册
     */
    fun register(userDto: RegisterDto): User {
        val count = userRepository.countByUsername(userDto.username)
        if (count > 0) {
            throw ErrorException(ErrorCode.USER_ALREADY_EXIST)
        }
        val encryptPassword = encoder.encode(userDto.password)
        val role = roleRepository.findByName("USER")
        val roles = if (role == null) {
            throw ErrorException(ErrorCode.ROLE_NOT_EXIST)
        } else {
            mutableSetOf(role)
        }
        val gender = if (userDto.gender == "female") Gender.FEMALE else Gender.MALE
        val cart = Cart()
        cartRepository.save(cart)
        val user = User(userDto.username, encryptPassword, gender, roles, cart)
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

    /**
     * 获取指定用户信息
     */
    fun getUser(username: String): User {
        val user = userRepository.findByUsername(username)
        return user ?: throw ErrorException(ErrorCode.USER_NOT_EXIST)
    }

}
