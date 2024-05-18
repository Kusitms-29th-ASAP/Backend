package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.domain.classroom.domain.exception.AnnouncementException
import com.asap.asapbackend.domain.classroom.domain.model.Announcement
import com.asap.asapbackend.domain.classroom.domain.repository.AnnouncementRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AnnouncementReader(
    private val announcementRepository: AnnouncementRepository
) {
    fun findRecentAnnouncementByClassroomIdOrNull(classroomId: Long): Announcement? {
        return announcementRepository.findTopByClassroomIdOrderByCreatedAtDesc(classroomId)
    }

    fun findAllByClassroomId(classroomId: Long): List<Announcement> {
        return announcementRepository.findAllByClassroomId(classroomId)
    }

    fun findById(announcementId: Long) : Announcement {
        return findAnnouncement {
            announcementRepository.findByIdOrNull(announcementId)
        }
    }

    private fun findAnnouncement(function: () -> Announcement?): Announcement {
        return function() ?: throw AnnouncementException.AnnouncementNotFound()
    }
}