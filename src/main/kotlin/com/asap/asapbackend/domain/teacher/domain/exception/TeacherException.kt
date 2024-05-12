package com.asap.asapbackend.domain.teacher.domain.exception

import com.asap.asapbackend.global.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

sealed class TeacherException(
    message: String,
    errorCode: Int,
    httpStatusCode: HttpStatusCode,
):BusinessException(DEFAULT_CODE_PREFIX, errorCode, httpStatusCode, message) {

    class TeacherNotFoundException(message: String = "선생님을 찾을 수 없습니다.") : TeacherException(message, 1, HttpStatus.NOT_FOUND)

    class DuplicateUsernameException(message: String = "중복된 아이디가 존재합니다.") : TeacherException(message, 2, HttpStatus.BAD_REQUEST)


    companion object{
        const val DEFAULT_CODE_PREFIX = "TEACHER"
    }
}