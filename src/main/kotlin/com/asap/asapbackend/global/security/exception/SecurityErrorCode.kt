package com.asap.asapbackend.global.security.exception

import com.asap.asapbackend.global.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

enum class SecurityErrorCode(
    override val code: String,
    override val message: String,
    override val httpStatusCode: HttpStatusCode
):ErrorCode {

    AUTHENTICATION_NOT_FOUND("3001", "인증 정보를 찾을 수 없습니다.", HttpStatus.UNAUTHORIZED),
}