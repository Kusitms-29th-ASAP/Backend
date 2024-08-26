package com.asap.asapbackend.infrastructure.jpa.timetable.repository

import com.asap.asapbackend.infrastructure.jpa.timetable.entity.SubjectEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SubjectJpaRepository : JpaRepository<SubjectEntity, Long>{
    fun findAllByClassroomIdIn(classroomIds: List<Long>): List<SubjectEntity>

    fun findAllByClassroomId(classroomId: Long): List<SubjectEntity>
}