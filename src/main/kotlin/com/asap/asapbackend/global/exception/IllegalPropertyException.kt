package com.asap.asapbackend.global.exception

class IllegalPropertyException(
    override val errorCode: ErrorCode
) : BusinessException(errorCode){
}