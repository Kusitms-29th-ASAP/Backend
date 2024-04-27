package com.asap.asapbackend.global.jwt.exception

import com.asap.asapbackend.global.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

enum class TokenErrorCode(
    override val code: String,
    override val message: String,
    override val httpStatusCode: HttpStatusCode
): ErrorCode {
    INVALID_TOKEN("9000", "유효하지 않는 토큰입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("9001", "만료된 토큰입니다.",HttpStatus.UNAUTHORIZED)
    ;
}
