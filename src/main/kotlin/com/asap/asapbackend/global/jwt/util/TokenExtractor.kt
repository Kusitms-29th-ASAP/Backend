package com.asap.asapbackend.global.jwt.util

import com.asap.asapbackend.global.jwt.exception.TokenException
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
        throw TokenException.UnsupportedTokenException()
    }
}