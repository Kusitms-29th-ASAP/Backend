package com.asap.asapbackend.global.jwt

import com.asap.asapbackend.global.util.CacheManager
import com.asap.asapbackend.global.util.LockManager
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Component
class JwtProvider( // 토큰을 캐싱하는 역할은 따로 제공할 예정
    private val jwtProperties: JwtProperties,
    private val jwtKeyFactory: JwtKeyFactory,
    private val jwtValidator: JwtValidator
) {

    private fun generateAccessToken(userClaims: PrivateClaims.UserClaims): String {
        return generateBasicToken(
            userClaims.createPrivateClaims(TokenType.ACCESS_TOKEN),
            jwtProperties.accessTokenExpirationTime
        )
    }

    private fun generateRefreshToken(userClaims: PrivateClaims.UserClaims): String {
        return generateBasicToken(
            userClaims.createPrivateClaims(TokenType.REFRESH_TOKEN),
            jwtProperties.refreshTokenExpirationTime
        )
    }

    fun generateToken(userClaims: PrivateClaims.UserClaims): Token {
        return Token(
            accessToken = generateAccessToken(userClaims),
            refreshToken = generateRefreshToken(userClaims)
        )
    }

    fun reissueToken(refreshToken: String): Token = LockManager.lockByKey(refreshToken) {
        CacheManager.cacheByKey(refreshToken){
            jwtValidator.validateToken(refreshToken, TokenType.REFRESH_TOKEN)
            val userClaims = extractUserClaimsFromToken(refreshToken, TokenType.REFRESH_TOKEN)
            val token = generateToken(userClaims)

            return@cacheByKey token
        }

    }

    fun extractUserClaimsFromToken(token: String, tokenType: TokenType): PrivateClaims.UserClaims {
        return jwtValidator.initializeJwtParser(tokenType)
            .parseSignedClaims(token)
            .payload
            .get(JwtConst.USER_CLAIMS, PrivateClaims.UserClaims::class.java)
    }

    private fun generateBasicToken(privateClaims: PrivateClaims, expireTime: Long): String {
        val now = Date()
        return Jwts.builder()
            .issuer(JwtConst.TOKEN_ISSUER)
            .claims(privateClaims.convertToClaims())
            .issuedAt(now)
            .expiration(Date(now.time + expireTime))
            .signWith(jwtKeyFactory.generateKey())
            .compact()
    }
}