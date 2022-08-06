package com.yuweihung.bookstore.config.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.yuweihung.bookstore.bean.dto.LoginDto
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 自定义登录认证过滤器，接收 JSON 参数而不是 FormData
 * @author Yu Weihong
 * @since 2022/7/20
 */
class LoginAuthenticationFilter : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        if (request != null) {
            if (request.contentType.equals(MediaType.APPLICATION_JSON_VALUE)) {
                val mapper = jacksonObjectMapper()
                val user = mapper.readValue(request.inputStream, LoginDto::class.java)
                val authRequest = UsernamePasswordAuthenticationToken(user.username.trim(), user.password)
                setDetails(request, authRequest)
                return this.authenticationManager.authenticate(authRequest)
            }
        }
        return super.attemptAuthentication(request, response)
    }
}
