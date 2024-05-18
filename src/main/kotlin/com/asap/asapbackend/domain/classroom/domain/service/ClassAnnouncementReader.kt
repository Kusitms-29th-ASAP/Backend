package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.domain.classroom.domain.model.ClassAnnouncement
import com.asap.asapbackend.domain.classroom.domain.repository.AnnouncementRepository
import org.springframework.stereotype.Service

@Service
class ClassAnnouncementReader(
    private val announcementRepository: AnnouncementRepository
) {
    fun getRecentAnnouncementByClassroomIdOrNull(classroomId: Long): ClassAnnouncement? {
        return announcementRepository.findTopByClassroomIdOrderByCreatedAtDesc(classroomId)
    }

    fun getAllByClassroomId(classroomId: Long): List<ClassAnnouncement> {
        return announcementRepository.findAllByClassroomId(classroomId)
    }
}