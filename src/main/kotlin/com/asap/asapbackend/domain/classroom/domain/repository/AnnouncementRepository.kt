package com.asap.asapbackend.domain.classroom.domain.repository

import com.asap.asapbackend.domain.classroom.domain.model.ClassAnnouncement
import org.springframework.data.jpa.repository.JpaRepository

interface AnnouncementRepository: JpaRepository<ClassAnnouncement,Long> {
    fun findTopByClassroomIdOrderByCreatedAtDesc(classroomId : Long) : ClassAnnouncement?

    fun findAllByClassroomId(classroomId : Long) : List<ClassAnnouncement>
}