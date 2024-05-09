package com.asap.asapbackend.global.security.exception


class AuthenticationNotFouncException(
    errorCode: SecurityErrorCode
) : SecurityException(errorCode) {
}