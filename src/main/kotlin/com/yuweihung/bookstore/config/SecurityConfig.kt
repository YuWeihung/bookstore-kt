package com.yuweihung.bookstore.config

import com.yuweihung.bookstore.config.security.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * Spring Security 配置类
 */
@Configuration
@EnableJpaAuditing
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
                authorize("/test/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            formLogin {
                permitAll()
            }
            logout {
                logoutSuccessHandler = logoutHandler
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
        // 替换默认登录认证过滤器
        http.addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
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
     * 自定义登录认证过滤器，实现 JSON 登录
     */
    @Bean
    fun loginAuthenticationFilter(): LoginAuthenticationFilter {
        val filter = LoginAuthenticationFilter()
        val authenticationManager = authenticationConfiguration.authenticationManager
        filter.setAuthenticationSuccessHandler(successHandler)
        filter.setAuthenticationFailureHandler(failureHandler)
        filter.setAuthenticationManager(authenticationManager)
        return filter
    }

}
