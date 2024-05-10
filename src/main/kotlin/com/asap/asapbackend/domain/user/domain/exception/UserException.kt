package com.asap.asapbackend.domain.user.domain.exception

import com.asap.asapbackend.global.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

sealed class UserException(
    message: String,
    errorCode: Int,
    httpStatusCode: HttpStatusCode,
) : BusinessException(DEFAULT_CODE_PREFIX, errorCode, httpStatusCode, message) {

    class UserNotFoundException(message: String = "사용자를 찾을 수 없습니다.") : UserException(message, 1, HttpStatus.NOT_FOUND)

    companion object {
        const val DEFAULT_CODE_PREFIX = "USER"
    }

}