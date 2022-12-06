package com.yuweihung.bookstore.service

import com.yuweihung.bookstore.bean.dto.LoginDto
import com.yuweihung.bookstore.bean.vo.LoginVo
import com.yuweihung.bookstore.bean.vo.TokenVo
import com.yuweihung.bookstore.common.Constant
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class JwtService(
    val jwtEncoder: JwtEncoder,
    val jwtDecoder: JwtDecoder,
    val authenticationConfiguration: AuthenticationConfiguration,
) {
    /**
     * 用户登录
     */
    fun login(loginDto: LoginDto): LoginVo {
        val username = loginDto.username.trim()
        val password = loginDto.password
        val authenticationManager = authenticationConfiguration.authenticationManager
        val authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(username, password)
        val authentication = authenticationManager.authenticate(authenticationToken)
        val now = Instant.now()
        val scope = authentication.authorities.joinToString(separator = " ") { it.authority }
        val accessToken = generateAccessToken(username, now, scope)
        val refreshToken = generateRefreshToken(username, now)
        val authoritiesList = authentication.authorities.map { it.authority }
        return LoginVo(username, authoritiesList, accessToken, refreshToken)
    }

    /**
     * 刷新 Token
     */
    fun refreshToken(token: String): TokenVo {
        val jwt = jwtDecoder.decode(token)
        val claims = jwt.claims
        val now = Instant.now()
        val username = claims["sub"].toString()
        val scope = claims["scope"].toString()
        val accessToken = generateAccessToken(username, now, scope)
        val refreshToken = generateRefreshToken(username, now)
        return TokenVo(accessToken, refreshToken)
    }

    /**
     * 生成 JWT Access Token
     */
    fun generateAccessToken(username: String, now: Instant, scope: String): String {
        val expiration = Constant.TOKEN_EXPIRATION_TIME
        val claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiration))
            .subject(username)
            .claim("scope", scope)
            .build()
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }

    /**
     * 生成 JWT Refresh Token
     */
    fun generateRefreshToken(username: String, now: Instant): String {
        val expiration = Constant.TOKEN_EXPIRATION_TIME * 3
        val scope = Constant.TOKEN_SCOPE
        val claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiration))
            .subject(username)
            .claim("scope", scope)
            .build()
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }
}
