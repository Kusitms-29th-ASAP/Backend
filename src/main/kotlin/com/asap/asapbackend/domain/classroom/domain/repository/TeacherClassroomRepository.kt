package com.asap.asapbackend.domain.classroom.domain.repository

import com.asap.asapbackend.domain.classroom.domain.model.TeacherClassroom
import org.springframework.data.jpa.repository.JpaRepository

interface TeacherClassroomRepository:JpaRepository<TeacherClassroom, Long>{
}