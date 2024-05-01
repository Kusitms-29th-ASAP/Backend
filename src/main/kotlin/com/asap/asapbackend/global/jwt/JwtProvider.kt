package com.asap.asapbackend.global.jwt

import com.asap.asapbackend.global.jwt.exception.TokenErrorCode
import com.asap.asapbackend.global.jwt.exception.TokenNotFoundException
import com.asap.asapbackend.global.util.CacheManager
import com.asap.asapbackend.global.util.LockManager
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtProvider( // 토큰을 캐싱하는 역할은 따로 제공할 예정
    private val jwtProperties: JwtProperties,
    private val jwtKeyFactory: JwtKeyFactory,
    private val jwtValidator: JwtValidator,
    private val jwtRegistry: JwtRegistry
) {

    fun generateAccessToken(user: Claims.UserClaims): String {
        return generateBasicToken(
            user.createPrivateClaims(TokenType.ACCESS_TOKEN),
            jwtProperties.accessTokenExpirationTime
        )
    }

    fun generateRefreshToken(user: Claims.UserClaims): String {
        val refreshToken =  generateBasicToken(
            user.createPrivateClaims(TokenType.REFRESH_TOKEN),
            jwtProperties.refreshTokenExpirationTime
        )
        jwtRegistry.upsert(user.userId to refreshToken)
        return refreshToken
    }

    fun generateRegistrationToken(registrationClaims: Claims.RegistrationClaims): String {
        return generateBasicToken(
            registrationClaims.createPrivateClaims(TokenType.REGISTRATION_TOKEN),
            jwtProperties.registrationTokenExpirationTime // TODO : registration token expire time
        )
    }

    fun reissueToken(refreshToken: String): Token = LockManager.lockByKey(refreshToken) {
        CacheManager.cacheByKey(refreshToken) {
            if(jwtRegistry.isExists(refreshToken).not()) {
                throw TokenNotFoundException(TokenErrorCode.TOKEN_NOT_FOUND)
            }
            jwtValidator.validateToken(refreshToken, TokenType.REFRESH_TOKEN)
            val userClaims: Claims.UserClaims = extractUserClaimsFromToken(refreshToken, TokenType.REFRESH_TOKEN)
            val accessToken = generateAccessToken(userClaims)
            val newRefreshToken = generateRefreshToken(userClaims)

            jwtRegistry.upsert(userClaims.userId to newRefreshToken)

            return@cacheByKey Token(accessToken, newRefreshToken)
        }
    }

    fun extractUserClaimsFromToken(
        token: String,
        tokenType: TokenType
    ): Claims.UserClaims = extractClaimsFromToken(token, tokenType, JwtConst.USER_CLAIMS)

    fun extractRegistrationClaimsFromToken(
        token: String,
        tokenType: TokenType
    ): Claims.RegistrationClaims = extractClaimsFromToken(token, tokenType, JwtConst.REGISTRATION_CLAIMS)

    private inline fun <reified T> extractClaimsFromToken(
        token: String,
        tokenType: TokenType,
        claimsKey: String,
    ): T {
        return jwtValidator.initializeJwtParser(tokenType)
            .parseSignedClaims(token)
            .payload
            .get(claimsKey, T::class.java)
    }

    private fun generateBasicToken(claims: PrivateClaims, expireTime: Long): String {
        val now = Date()
        return Jwts.builder()
            .issuer(JwtConst.TOKEN_ISSUER)
            .claims(claims.convertToClaims())
            .issuedAt(now)
            .expiration(Date(now.time + expireTime))
            .signWith(jwtKeyFactory.generateKey())
            .compact()
    }
}