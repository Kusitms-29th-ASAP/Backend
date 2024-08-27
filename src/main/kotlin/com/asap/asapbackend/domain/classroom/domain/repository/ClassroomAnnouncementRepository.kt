package com.asap.asapbackend.domain.classroom.domain.repository

import com.asap.asapbackend.domain.classroom.domain.model.ClassroomAnnouncement

interface ClassroomAnnouncementRepository{
    fun findTopByClassroomIdOrderByCreatedAtDesc(classroomId : Long) : ClassroomAnnouncement?

    fun findAllByClassroomIdOrderByCreatedAtDesc(classroomId : Long) : List<ClassroomAnnouncement>

    fun findByIdOrNull(classroomAnnouncementId : Long) : ClassroomAnnouncement?

    fun save(classroomAnnouncement : ClassroomAnnouncement) : ClassroomAnnouncement
}