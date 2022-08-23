package com.yuweihung.bookstore.config.security

import com.yuweihung.bookstore.response.ErrorCode
import com.yuweihung.bookstore.response.Response
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 注销成功后返回信息
 */
@Component
class LogoutHandler : LogoutSuccessHandler {
    override fun onLogoutSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?,
    ) {

        val result = if (authentication == null) {
            Response.failure(ErrorCode.USER_NOT_LOGIN)
        } else {
            Response.success()
        }
        response?.let { Response.render(it, result) }
    }
}
