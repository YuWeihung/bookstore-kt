package com.yuweihung.bookstore.config.security

import com.yuweihung.bookstore.common.ErrorCode
import com.yuweihung.bookstore.common.Response
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

/**
 * 用户无权访问时返回信息
 */
@Component
class DeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?,
    ) {
        val result = Response.failure(ErrorCode.FORBIDDEN)
        response?.let { Response.render(it, result) }
    }
}
