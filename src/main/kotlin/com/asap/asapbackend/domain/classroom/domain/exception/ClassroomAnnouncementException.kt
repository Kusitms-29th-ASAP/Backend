package com.asap.asapbackend.domain.classroom.domain.exception

import com.asap.asapbackend.global.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

sealed class ClassroomAnnouncementException (
    message: String,
    errorCode: Int,
    httpStatusCode: HttpStatusCode,
): BusinessException(DEFAULT_CODE_PREFIX, errorCode, httpStatusCode, message) {

    class ClassroomAnnouncementNotFound(message: String = "알림장을을 찾을 수 없습니다.") : ClassroomAnnouncementException(message, 1, HttpStatus.NOT_FOUND)

    companion object{
        const val DEFAULT_CODE_PREFIX = "ANNOUNCEMENT"
    }
}