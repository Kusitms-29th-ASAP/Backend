package com.asap.asapbackend.domain.teacher.domain.exception

import com.asap.asapbackend.global.exception.BusinessException

class TeacherException(
    errorCode: TeacherErrorCode
):BusinessException(errorCode) {
}