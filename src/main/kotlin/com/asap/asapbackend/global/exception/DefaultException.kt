package com.asap.asapbackend.global.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

sealed class DefaultException(
    message: String,
    errorCode: Int,
    httpStatusCode: HttpStatusCode,
) : BusinessException(DEFAULT_CODE_PREFIX, errorCode, httpStatusCode, message){

    class IllegalPropertyException(message: String = DEFAULT_MESSAGE) : DefaultException(message, 1, HttpStatus.BAD_REQUEST){
        companion object{
            const val DEFAULT_MESSAGE = "잘못된 속성입니다."
        }
    }

    class InternalServerException(message: String = DEFAULT_MESSAGE) : DefaultException(message, 2, HttpStatus.INTERNAL_SERVER_ERROR){
        companion object{
            const val DEFAULT_MESSAGE = "예상하지 못한 오류가 발생했습니다."
        }
    }

    companion object{
        const val DEFAULT_CODE_PREFIX = "DEFAULT"
    }
}


inline fun validateProperty(value: Boolean, lazyMessage: () -> String = {DefaultException.IllegalPropertyException.DEFAULT_MESSAGE}){
    if(!value){
        throw DefaultException.IllegalPropertyException(lazyMessage())
    }
}