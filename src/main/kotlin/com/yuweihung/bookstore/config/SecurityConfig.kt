package com.yuweihung.bookstore.config

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.SecurityContext
import com.yuweihung.bookstore.common.Constant
import com.yuweihung.bookstore.config.security.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
class SecurityConfig(
    val deniedHandler: DeniedHandler,
    val entryPointHandler: EntryPointHandler,
    @Value("\${jwt.public.key}")
    val key: RSAPublicKey,
    @Value("\${jwt.private.key}")
    val priv: RSAPrivateKey,
) {
    /**
     * 权限相关认证
     */
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/admin/**", hasAuthority("ROLE_ADMIN"))
                authorize("/login", permitAll)
                authorize("/refresh-token", hasAuthority(Constant.TOKEN_SCOPE))
                authorize("/register", permitAll)
                authorize("/index", permitAll)
                authorize("/search/**", permitAll)
                authorize("/test/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            oauth2ResourceServer {
                authenticationEntryPoint = entryPointHandler
                jwt {
                    jwtAuthenticationConverter = jwtAuthenticationConverter()
                }

            }
            sessionManagement {
                sessionConcurrency {
                    sessionCreationPolicy = SessionCreationPolicy.STATELESS
                }
            }
            exceptionHandling {
                accessDeniedHandler = deniedHandler
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
     * JWT 编码器
     */
    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(this.key).build()
    }

    /**
     * JWT 解码器
     */
    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk = RSAKey.Builder(this.key).privateKey(this.priv).build()
        val jwks = ImmutableJWKSet<SecurityContext>(JWKSet(jwk))
        return NimbusJwtEncoder(jwks)
    }

    /**
     * JWT 生成权限时不加前缀
     */
    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val grantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        grantedAuthoritiesConverter.setAuthorityPrefix("")

        val jwtAuthenticationConverter = JwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter)
        return jwtAuthenticationConverter
    }

}
