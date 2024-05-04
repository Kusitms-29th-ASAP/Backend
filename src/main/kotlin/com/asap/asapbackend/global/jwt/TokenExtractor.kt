package com.asap.asapbackend.global.jwt

import com.asap.asapbackend.global.jwt.exception.InvalidTokenException
import com.asap.asapbackend.global.jwt.exception.TokenErrorCode
import org.springframework.stereotype.Component

@Component
class TokenExtractor {
    object Constants {
        const val HEADER = "Authorization"
        const val PREFIX = "Bearer "
    }

    fun extractValue(token: String): String {
        if(token.startsWith(Constants.PREFIX)) {
            return token.substring(Constants.PREFIX.length)
        }
        throw InvalidTokenException(TokenErrorCode.INVALID_TOKEN)
    }
}