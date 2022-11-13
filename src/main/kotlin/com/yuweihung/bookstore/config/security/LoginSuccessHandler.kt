package com.yuweihung.bookstore.config.security

import com.yuweihung.bookstore.common.Response
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

/**
 * 登录成功返回角色权限
 */
@Component
class LoginSuccessHandler : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?,
    ) {
        if (response != null && authentication != null) {
            val roles = authentication.authorities.map { it.authority.toString() }
            val role: String = if (roles.contains("ROLE_ADMIN")) "admin" else "user"
            Response.render(response, Response.success(role))
        }
    }
}
