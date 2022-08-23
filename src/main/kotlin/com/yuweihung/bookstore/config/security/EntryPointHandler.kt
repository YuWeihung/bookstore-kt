package com.yuweihung.bookstore.config.security

import com.yuweihung.bookstore.response.ErrorCode
import com.yuweihung.bookstore.response.Response
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 用户未登录时返回信息
 */
@Component
class EntryPointHandler : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?,
    ) {
        val result = Response.failure(ErrorCode.USER_NOT_LOGIN)
        response?.let { Response.render(it, result) }
    }
}
