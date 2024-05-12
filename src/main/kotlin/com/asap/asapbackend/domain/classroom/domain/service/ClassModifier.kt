package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomRepository
import org.springframework.stereotype.Service

@Service
class ClassModifier(
    private val classroomRepository: ClassroomRepository,
) {

    fun update(classroom: Classroom){
        classroom.validatePersisted()
        classroomRepository.save(classroom)
    }
}