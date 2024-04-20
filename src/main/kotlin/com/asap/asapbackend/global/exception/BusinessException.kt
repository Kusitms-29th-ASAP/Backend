package com.asap.asapbackend.global.exception

open class BusinessException(
    open val errorCode: ErrorCode
) : RuntimeException(errorCode.message) {
}