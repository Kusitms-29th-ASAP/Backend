package com.asap.asapbackend.global.security.exception

import com.asap.asapbackend.global.exception.BusinessException

open class SecurityException(
    errorCode: SecurityErrorCode
) : BusinessException(errorCode) {
}