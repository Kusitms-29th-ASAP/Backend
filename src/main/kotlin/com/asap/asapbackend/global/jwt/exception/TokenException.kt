package com.asap.asapbackend.global.jwt.exception

import com.asap.asapbackend.global.exception.BusinessException
import com.asap.asapbackend.global.exception.ErrorCode

open class TokenException(
    override val errorCode: ErrorCode
) : BusinessException(errorCode) {
}