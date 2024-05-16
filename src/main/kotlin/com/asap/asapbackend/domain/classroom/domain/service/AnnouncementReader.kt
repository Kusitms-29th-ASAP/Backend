package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.domain.classroom.domain.model.Announcement
import com.asap.asapbackend.domain.classroom.domain.repository.AnnouncementRepository
import org.springframework.stereotype.Service

@Service
class AnnouncementReader(
    private val announcementRepository: AnnouncementRepository
) {
    fun getRecentAnnouncementByClassroomIdOrNull(classroomId: Long): Announcement? {
        return announcementRepository.findTopByClassroomIdOrderByCreatedAtDesc(classroomId)
    }

    fun getAllByClassroomId(classroomId: Long): List<Announcement> {
        return announcementRepository.findAllByClassroomId(classroomId)
    }
}