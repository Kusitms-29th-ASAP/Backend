package com.asap.asapbackend.domain.teacher.domain.exception

import com.asap.asapbackend.global.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

enum class TeacherErrorCode(
    override val code: String,
    override val message: String,
    override val httpStatusCode: HttpStatusCode,
) : ErrorCode {
    TEACHER_NOT_FOUND(
        code = "3001",
        message = "존재하지 않는 선생님입니다.",
        httpStatusCode = HttpStatus.NOT_FOUND
    ),
    TEACHER_USERNAME_DUPLICATED(
        code = "3002",
        message = "이미 존재하는 아이디입니다.",
        httpStatusCode = HttpStatus.BAD_REQUEST
    )
    ;


}