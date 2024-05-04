package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.model.Grade
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomRepository
import org.springframework.stereotype.Service

@Service
class ClassroomReader(
    private val classroomRepository: ClassroomRepository
) {
    // TODO : 예외 정의하기
    fun findByGradeAndClassNumberAndSchoolId(grade: Grade, classNumber: String, schoolId: Long): Classroom {
        return classroomRepository.findByGradeAndClassNumberAndSchoolId(grade, classNumber, schoolId)
            ?: throw IllegalArgumentException("Classroom not found")
    }
}