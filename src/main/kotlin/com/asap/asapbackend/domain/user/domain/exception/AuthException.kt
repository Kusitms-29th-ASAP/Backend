package com.asap.asapbackend.domain.user.domain.exception

import com.asap.asapbackend.global.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

sealed class AuthException(
    message: String,
    errorCode: Int,
    httpStatusCode: HttpStatusCode
): BusinessException(DEFAULT_CODE_PREFIX, errorCode, httpStatusCode, message) {

    class AuthFailedException(message: String = "인증에 실패했습니다.") : AuthException(message, 1, HttpStatus.BAD_REQUEST)

    class UnSupportedProviderException(message: String = "지원하지 않는 제공자입니다.") : AuthException(message, 2, HttpStatus.BAD_REQUEST)

    companion object {
        const val DEFAULT_CODE_PREFIX = "AUTH"
    }
}