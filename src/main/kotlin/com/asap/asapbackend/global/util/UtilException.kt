package com.asap.asapbackend.global.util

import com.asap.asapbackend.global.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

sealed class UtilException(
    message: String,
    errorCode: Int,
    httpStatusCode: HttpStatusCode
) : BusinessException(ERROR_CODE, errorCode, httpStatusCode, message) {

    class ImageToTextConversionException(message: String = "이미지를 텍스트로 변환하는 중 오류가 발생했습니다.") :
        UtilException(message, 1, HttpStatus.INTERNAL_SERVER_ERROR)

    class TextSummarizationException(
        message: String = "텍스트 요약 중 오류가 발생했습니다.",
    ) : UtilException(message, 2, HttpStatus.INTERNAL_SERVER_ERROR)

    class TextKeywordExtractionException(
        message: String = "텍스트 키워드 추출 중 오류가 발생했습니다.",
    ) : UtilException(message, 3, HttpStatus.INTERNAL_SERVER_ERROR)

    companion object {
        private const val ERROR_CODE = "UTIL_ERROR"
    }
}