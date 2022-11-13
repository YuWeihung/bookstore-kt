package com.yuweihung.bookstore.config

import com.yuweihung.bookstore.config.security.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
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
) {
    /**
     * 权限相关认证
     */
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                    .requestMatchers("/register/**").anonymous()
                    .requestMatchers("/index/**").permitAll()
                    .requestMatchers("/search/**").permitAll()
                    .requestMatchers("/test/**").permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { login ->
                login
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
                    .permitAll()
            }
            .logout { logout ->
                logout
                    .logoutSuccessHandler(logoutHandler)
                    .permitAll()
            }
            .exceptionHandling { exception ->
                exception
                    .accessDeniedHandler(deniedHandler)
                    .authenticationEntryPoint(entryPointHandler)
            }
            .sessionManagement { session ->
                session
                    .maximumSessions(1)
            }
            .csrf { csrf ->
                csrf
                    .disable()
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

}
