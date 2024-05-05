package com.asap.asapbackend.domain.classroom.domain.exception

class ClassroomNotFoundException(
    errorCode: ClassroomErrorCode
):ClassroomException(errorCode) {
}