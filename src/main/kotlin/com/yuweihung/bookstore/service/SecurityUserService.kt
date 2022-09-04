package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.entity.User
import com.yuweihung.bookstore.common.ErrorCode
import com.yuweihung.bookstore.common.ErrorException
import com.yuweihung.bookstore.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

/**
 * UserDetailsService 接口的实现，实现 Spring Security 的登录功能
 */
@Service
class SecurityUserService(val userRepository: UserRepository) : UserDetailsService {
    /**
     * 从数据库中查找用户
     */
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = username?.let { userRepository.findByUsername(it) }
        if (user == null) {
            throw ErrorException(ErrorCode.USER_NOT_EXIST)
        } else {
            return SecurityUserDetails(user)
        }
    }

    /**
     * 实现 UserDetails 接口，将自定义 User 类包装成 UserDetails
     */
    class SecurityUserDetails(private val user: User) : UserDetails {

        /**
         * 封装用户权限
         */
        override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
            val authorities = mutableSetOf<GrantedAuthority>()
            for (role in user.roles) {
                authorities.add(SimpleGrantedAuthority("ROLE_${role.name}"))
            }
            return authorities
        }

        override fun getPassword(): String {
            return user.password
        }

        override fun getUsername(): String {
            return user.username
        }

        override fun isAccountNonExpired(): Boolean {
            return true
        }

        override fun isAccountNonLocked(): Boolean {
            return true
        }

        override fun isCredentialsNonExpired(): Boolean {
            return true
        }

        override fun isEnabled(): Boolean {
            return true
        }

    }
}
