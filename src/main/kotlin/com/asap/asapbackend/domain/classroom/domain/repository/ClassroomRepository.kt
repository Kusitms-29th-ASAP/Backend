package com.asap.asapbackend.domain.classroom.domain.repository

import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.model.Grade
import org.springframework.data.jpa.repository.JpaRepository

interface ClassroomRepository: JpaRepository<Classroom, Long> {
    fun findByGradeAndClassNumberAndSchoolId(grade: Grade, classNumber: String, schoolId: Long): Classroom?
}