package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.dto.ChangePasswordDto
import com.yuweihung.bookstore.bean.dto.LoginDto
import com.yuweihung.bookstore.bean.dto.RegisterDto
import com.yuweihung.bookstore.bean.entity.Cart
import com.yuweihung.bookstore.bean.entity.Gender
import com.yuweihung.bookstore.bean.entity.User
import com.yuweihung.bookstore.bean.vo.LoginVo
import com.yuweihung.bookstore.bean.vo.UserVo
import com.yuweihung.bookstore.common.Constant
import com.yuweihung.bookstore.common.ErrorCode
import com.yuweihung.bookstore.common.ErrorException
import com.yuweihung.bookstore.repository.CartRepository
import com.yuweihung.bookstore.repository.RoleRepository
import com.yuweihung.bookstore.repository.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

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
    val jwtEncoder: JwtEncoder,
    val authenticationConfiguration: AuthenticationConfiguration,
) {
    /**
     * 用户登录
     */
    fun login(loginDto: LoginDto): LoginVo {
        val username = loginDto.username.trim()
        val password = loginDto.password
        val authenticationManager = authenticationConfiguration.authenticationManager
        val authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(username, password)
        val authentication = authenticationManager.authenticate(authenticationToken)
        val now = Instant.now()
        val expiry = Constant.TOKEN_EXPIRATION_TIME
        val scope = authentication.authorities.joinToString(separator = " ") { it.authority }
        val claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiry))
            .subject(authentication.name)
            .claim("scope", scope)
            .build()
        val jwtToken = jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
        val authoritiesList = authentication.authorities.map { it.authority }
        return LoginVo(username, authoritiesList, jwtToken)
    }

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
    fun changePassword(changePasswordDto: ChangePasswordDto): UserVo {
        val user = userRepository.findByUsername(changePasswordDto.username)
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
