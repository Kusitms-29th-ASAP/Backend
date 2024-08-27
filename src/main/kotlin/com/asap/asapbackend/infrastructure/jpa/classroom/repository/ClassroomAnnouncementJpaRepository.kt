package com.asap.asapbackend.infrastructure.jpa.classroom.repository

import com.asap.asapbackend.infrastructure.jpa.classroom.entity.ClassroomAnnouncementEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ClassroomAnnouncementJpaRepository : JpaRepository<ClassroomAnnouncementEntity, Long>{
    fun findTopByClassroomIdOrderByCreatedAtDesc(classroomId: Long): ClassroomAnnouncementEntity?
    fun findAllByClassroomIdOrderByCreatedAtDesc(classroomId: Long): List<ClassroomAnnouncementEntity>


}