package com.yuweihung.bookstore.config.security

import com.yuweihung.bookstore.response.ErrorCode
import com.yuweihung.bookstore.response.Response
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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
