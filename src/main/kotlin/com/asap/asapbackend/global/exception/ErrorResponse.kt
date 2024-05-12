package com.asap.asapbackend.global.exception

data class ErrorResponse(
    val errorCode: String,
    val message: String,
) {
}