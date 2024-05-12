package com.asap.asapbackend.global.jwt.util

import com.asap.asapbackend.global.jwt.exception.TokenException
import com.asap.asapbackend.global.jwt.vo.JwtConst
import com.asap.asapbackend.global.jwt.vo.PrivateClaims
import com.asap.asapbackend.global.jwt.vo.TokenType
import io.jsonwebtoken.*
import io.jsonwebtoken.jackson.io.JacksonDeserializer
import io.jsonwebtoken.security.SignatureException
import org.springframework.stereotype.Component

@Component
class JwtValidator(
    private val jwtKeyFactory: JwtKeyFactory
) {

    fun validateToken(token: String, tokenType: TokenType) {
        val jwtParser = initializeJwtParser(tokenType)
        try {
            jwtParser.parse(token)
        } catch (ex: Exception) {
            when (ex) {
                is MalformedJwtException,
                is SignatureException,
                is IncorrectClaimException,
                is IllegalArgumentException -> throw TokenException.InvalidTokenException()

                is ExpiredJwtException -> throw TokenException.ExpiredTokenException()
                else -> throw ex
            }
        }
    }


    fun initializeJwtParser(tokenType: TokenType): JwtParser {
        return Jwts.parser()
            .json(JacksonDeserializer(PrivateClaims.retrieveClaimsClassType()))
            .verifyWith(jwtKeyFactory.generateKey())
            .requireIssuer(JwtConst.TOKEN_ISSUER)
            .require(JwtConst.TOKEN_TYPE, tokenType)
            .build()
    }
}