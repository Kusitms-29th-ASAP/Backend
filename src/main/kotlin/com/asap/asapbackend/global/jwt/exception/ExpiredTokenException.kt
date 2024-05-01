package com.asap.asapbackend.global.jwt.exception

import com.asap.asapbackend.global.exception.ErrorCode

class ExpiredTokenException(
    override val errorCode: ErrorCode
): TokenException(errorCode)