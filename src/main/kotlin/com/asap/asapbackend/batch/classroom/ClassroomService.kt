package com.asap.asapbackend.batch.classroom

import com.asap.asapbackend.domain.classroom.domain.service.ClassroomAppender
import org.springframework.stereotype.Service

@Service
class ClassroomService(
    private val classroomReader: ClassroomOpenApiClient,
    private val classroomAppender: ClassroomAppender
) {
    fun addClassroom() {
        val classroom = classroomReader.getClassroom()
        classroomAppender.addClassroom(classroom)
    }
}