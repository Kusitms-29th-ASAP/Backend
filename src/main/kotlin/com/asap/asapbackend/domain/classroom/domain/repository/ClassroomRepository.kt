package com.asap.asapbackend.domain.classroom.domain.repository

import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import org.springframework.data.jpa.repository.JpaRepository

interface ClassroomRepository: JpaRepository<Classroom, Long> {
    override fun deleteAll()
}