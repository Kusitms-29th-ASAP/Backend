package com.asap.asapbackend.domain.child.domain.exception

import com.asap.asapbackend.global.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

open class ChildException(
    message: String,
    errorCode: Int,
    httpStatusCode: HttpStatusCode,
) : BusinessException(DEFAULT_CODE_PREFIX, errorCode, httpStatusCode, message){

    class ChildNotFoundException(message: String = "자녀를 찾을 수 없습니다.") : ChildException(message, 1, HttpStatus.NOT_FOUND)

    class ChildAccessDeniedException(message: String = "자녀의 부모만 접근 가능합니다.") : ChildException(message, 2, HttpStatus.FORBIDDEN)

    companion object{
        const val DEFAULT_CODE_PREFIX = "CHILD"
    }
}