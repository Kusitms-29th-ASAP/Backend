package com.asap.asapbackend.domain.classroom.domain.exception

import com.asap.asapbackend.global.exception.BusinessException

open class ClassroomException(
    errorCode: ClassroomErrorCode
): BusinessException(errorCode) {
}