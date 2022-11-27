package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.dto.ChangePasswordDto
import com.yuweihung.bookstore.bean.dto.RegisterDto
import com.yuweihung.bookstore.bean.entity.Cart
import com.yuweihung.bookstore.bean.entity.Gender
import com.yuweihung.bookstore.bean.entity.User
import com.yuweihung.bookstore.bean.vo.UserVo
import com.yuweihung.bookstore.common.ErrorCode
import com.yuweihung.bookstore.common.ErrorException
import com.yuweihung.bookstore.repository.CartRepository
import com.yuweihung.bookstore.repository.RoleRepository
import com.yuweihung.bookstore.repository.UserRepository
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
    val passwordEncoder: PasswordEncoder,

) {


    /**
     * 用户注册
     */
    fun register(registerDto: RegisterDto): UserVo {
        val count = userRepository.countByUsername(registerDto.username)
        if (count > 0) {
            throw ErrorException(ErrorCode.USER_ALREADY_EXIST)
        }
        val encryptPassword = passwordEncoder.encode(registerDto.password)
        val role = roleRepository.findByName("USER")
        val roles = if (role == null) {
            throw ErrorException(ErrorCode.ROLE_NOT_EXIST)
        } else {
            mutableSetOf(role)
        }
        val gender = if (registerDto.gender == "F") Gender.F else Gender.M
        val cart = Cart()
        cartRepository.save(cart)
        val user = User(registerDto.username, encryptPassword, gender, roles, cart)
        val result = userRepository.save(user)
        return UserVo(result)
    }

    /**
     * 更改密码
     */
    fun changePassword(changePasswordDto: ChangePasswordDto, username: String): UserVo {
        val user = userRepository.findByUsername(username)
        if (user == null) {
            throw ErrorException(ErrorCode.USER_NOT_EXIST)
        } else if (!passwordEncoder.matches(changePasswordDto.oldPassword, user.password)) {
            throw ErrorException(ErrorCode.PASSWORD_WRONG)
        } else {
            val encryptPassword = passwordEncoder.encode(changePasswordDto.newPassword)
            user.password = encryptPassword
            val result = userRepository.save(user)
            return UserVo(result)
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
