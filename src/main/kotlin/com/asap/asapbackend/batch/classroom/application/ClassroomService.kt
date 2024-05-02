package com.asap.asapbackend.batch.classroom.application

import com.asap.asapbackend.batch.classroom.service.ClassroomAppender
import com.asap.asapbackend.batch.classroom.service.ClassroomReader
import org.springframework.stereotype.Service

@Service
class ClassroomService(
        private val classroomReader: ClassroomReader,
        private val classroomAppender: ClassroomAppender
) {
    fun addClassroom() {
        val classroom = classroomReader.getClassroom()
        classroomAppender.addClassroom(classroom)
    }
}