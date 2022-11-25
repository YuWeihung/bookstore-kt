package com.yuweihung.bookstore.config

import com.yuweihung.bookstore.config.security.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
class SecurityConfig(
    val successHandler: LoginSuccessHandler,
    val failureHandler: LoginFailureHandler,
    val logoutHandler: LogoutHandler,
    val deniedHandler: DeniedHandler,
    val entryPointHandler: EntryPointHandler,
    val authenticationConfiguration: AuthenticationConfiguration,
) {
    /**
     * 权限相关认证
     */
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeRequests {
                authorize("/admin/**", hasAuthority("ROLE_ADMIN"))
                authorize("/register/**", permitAll)
                authorize("/index/**", permitAll)
                authorize("/search/**", permitAll)
                authorize("/test/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            formLogin {
                authenticationSuccessHandler = successHandler
                authenticationFailureHandler = failureHandler
                permitAll()
            }
            logout {
                logoutSuccessHandler = logoutHandler
                deleteCookies("JSESSIONID")
                permitAll()
            }
            exceptionHandling {
                accessDeniedHandler = deniedHandler
                authenticationEntryPoint = entryPointHandler
            }
            csrf {
                disable()
            }
        }
        return http.build()
    }

    /**
     * 设置默认密码加密算法为 Bcrypt
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /**
     * 获取 AuthenticationManager
     */
    @Bean
    fun authenticationManager(): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

}
