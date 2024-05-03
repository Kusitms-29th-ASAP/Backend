package com.asap.asapbackend.domain.classroom.domain.exception

import com.asap.asapbackend.global.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

enum class ClassroomErrorCode(
    override val code: String,
    override val message: String,
    override val httpStatusCode: HttpStatusCode
): ErrorCode {
    GRADE_FORMAT_ERROR("2000", "학년 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
}