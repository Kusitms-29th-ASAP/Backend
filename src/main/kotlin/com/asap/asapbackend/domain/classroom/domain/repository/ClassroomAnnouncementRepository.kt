package com.asap.asapbackend.domain.classroom.domain.repository

import com.asap.asapbackend.domain.classroom.domain.model.ClassroomAnnouncement
import org.springframework.data.jpa.repository.JpaRepository

interface ClassroomAnnouncementRepository: JpaRepository<ClassroomAnnouncement,Long> {
    fun findTopByClassroomIdOrderByCreatedAtDesc(classroomId : Long) : ClassroomAnnouncement?

    fun findAllByClassroomId(classroomId : Long) : List<ClassroomAnnouncement>
}