package com.asap.asapbackend.global.jwt.exception

import com.asap.asapbackend.global.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

sealed class TokenException(
    message: String,
    errorCode: Int,
    httpStatusCode: HttpStatusCode,
) : BusinessException(DEFAULT_CODE_PREFIX, errorCode, httpStatusCode, message) {

    class InvalidTokenException(message: String = DEFAULT_MESSAGE) : TokenException(message, 1, HttpStatus.UNAUTHORIZED){
        companion object{
            const val DEFAULT_MESSAGE = "유효하지 않은 토큰입니다."
        }
    }

    class ExpiredTokenException(message: String = DEFAULT_MESSAGE) : TokenException(message, 2, HttpStatus.UNAUTHORIZED){
        companion object{
            const val DEFAULT_MESSAGE = "만료된 토큰입니다."
        }
    }

    class UnsupportedTokenException(message: String = DEFAULT_MESSAGE) : TokenException(message, 3, HttpStatus.UNAUTHORIZED){
        companion object{
            const val DEFAULT_MESSAGE = "지원하지 않는 토큰입니다."
        }
    }

    class TokenNotFoundException(message: String = DEFAULT_MESSAGE) : TokenException(message, 4, HttpStatus.UNAUTHORIZED){
        companion object{
            const val DEFAULT_MESSAGE = "토큰이 존재하지 않습니다."
        }
    }

    companion object{
        const val DEFAULT_CODE_PREFIX = "TOKEN"
    }
}