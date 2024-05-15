package com.asap.asapbackend.domain.todo.domain.exception

import com.asap.asapbackend.global.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

sealed class TodoException(
    message: String,
    errorCode: Int,
    httpStatusCode: HttpStatusCode,
) : BusinessException(DEFAULT_CODE_PREFIX, errorCode, httpStatusCode, message) {

    class TodoNotFoundException(message: String = "할일을 찾을 수 없습니다.") :
        TodoException(message, 1, HttpStatus.NOT_FOUND)

    companion object {
        const val DEFAULT_CODE_PREFIX = "TODO"
    }
}