package com.asap.asapbackend.domain.announcement.domain.exception

import com.asap.asapbackend.global.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

sealed class AnnouncementException(
    message: String,
    errorCode: Int,
    httpStatusCode: HttpStatusCode
) : BusinessException(DEFAULT_CODE_PREFIX, errorCode, httpStatusCode, message) {

    class AnnouncementNotFoundException(message: String = "공지사항을 찾을 수 없습니다.") : AnnouncementException(message, 1, HttpStatus.NOT_FOUND)

    companion object{
        const val DEFAULT_CODE_PREFIX = "ANNOUNCEMENT"
    }
}