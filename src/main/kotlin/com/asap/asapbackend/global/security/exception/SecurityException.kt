package com.asap.asapbackend.global.security.exception

import com.asap.asapbackend.global.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

open class SecurityException(
    message: String,
    errorCode: Int,
    httpStatusCode: HttpStatusCode,
) : BusinessException(DEFAULT_CODE_PREFIX, errorCode, httpStatusCode, message) {

    class AuthenticationNotFoundException(message: String = DEFAULT_MESSAGE) : SecurityException(message, 1, HttpStatus.UNAUTHORIZED){
        companion object{
            const val DEFAULT_MESSAGE = "인증 정보를 찾을 수 없습니다."
        }
    }

    companion object {
        const val DEFAULT_CODE_PREFIX = "SECURITY"
    }
}