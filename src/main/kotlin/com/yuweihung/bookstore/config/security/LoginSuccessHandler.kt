package com.yuweihung.bookstore.config.security

import com.yuweihung.bookstore.response.Response
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 登录成功返回响应
 * @author Yu Weihong
 * @since 2022/7/20
 */
@Component
class LoginSuccessHandler : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?,
    ) {
        response?.let { Response.render(it, Response.success()) }
    }
}
