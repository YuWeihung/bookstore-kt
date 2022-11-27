package com.yuweihung.bookstore.config.security

import com.yuweihung.bookstore.common.ErrorCode
import com.yuweihung.bookstore.common.Response
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

/**
 * 用户未登录时返回信息
 */
@Component
class EntryPointHandler : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?,
    ) {
        logger.info { authException.toString() }
        val result = if (authException is InvalidBearerTokenException) {
            Response.failure(ErrorCode.TOKEN_EXPIRED)
        } else {
            Response.failure(ErrorCode.USER_NOT_LOGIN)
        }
        response?.let { Response.render(it, result) }
    }
}
