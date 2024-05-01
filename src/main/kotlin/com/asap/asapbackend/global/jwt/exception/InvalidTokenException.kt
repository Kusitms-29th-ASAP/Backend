package com.asap.asapbackend.global.jwt.exception

import com.asap.asapbackend.global.exception.ErrorCode

class InvalidTokenException(
    override val errorCode: ErrorCode
): TokenException(errorCode)