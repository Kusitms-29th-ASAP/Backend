package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomRepository
import org.springframework.stereotype.Service

@Service
class ClassroomAppender(
    private val classroomRepository: ClassroomRepository,
) {

    fun addClassroom(classrooms: List<Classroom>) { // TODO: appendAll
        classrooms.forEach {
            classroomRepository.save(it)
        }
    }
}