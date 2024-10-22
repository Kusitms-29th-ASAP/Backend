package com.asap.asapbackend.global.exception

import org.springframework.http.HttpStatusCode

abstract class BusinessException(
    codePrefix: String = DEFAULT_CODE_PREFIX,
    errorCode: Int,
    httpStatusCode: HttpStatusCode,
    override val message: String = DEFAULT_MESSAGE,
) : RuntimeException(message) {

    val code: String = "$codePrefix-${
        errorCode.toString().padStart(DEFAULT_CODE_NUMBER_LENGTH, DEFAULT_CODE_NUMBER_PAD_CHAR)
    }"

    val httpStatusCode: HttpStatusCode = httpStatusCode

    companion object {
        const val DEFAULT_CODE_PREFIX = "UNKNOWN"
        const val DEFAULT_MESSAGE = "예상하지 못한 오류가 발생했습니다."
        const val DEFAULT_CODE_NUMBER_LENGTH = 3
        const val DEFAULT_CODE_NUMBER_PAD_CHAR = '0'
    }

}