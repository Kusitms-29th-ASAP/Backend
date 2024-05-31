package com.asap.asapbackend.domain.school.domain.exception

import com.asap.asapbackend.global.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

sealed class SchoolException(
    message: String,
    errorCode: Int,
    httpStatusCode: HttpStatusCode
): BusinessException(DEFAULT_CODE_PREFIX, errorCode, httpStatusCode, message) {

    class SchoolNotFoundException(message: String = "학교를 찾을 수 없습니다.") : SchoolException(message, 1, HttpStatus.NOT_FOUND)

    companion object {
        const val DEFAULT_CODE_PREFIX = "SCHOOL"
    }


}