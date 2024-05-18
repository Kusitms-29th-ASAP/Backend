package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.domain.classroom.domain.exception.AnnouncementException
import com.asap.asapbackend.domain.classroom.domain.model.ClassroomAnnouncement
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomAnnouncementRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ClassroomAnnouncementReader(
    private val classroomAnnouncementRepository: ClassroomAnnouncementRepository
) {
    fun findRecentAnnouncementByClassroomIdOrNull(classroomId: Long): ClassroomAnnouncement? {
        return classroomAnnouncementRepository.findTopByClassroomIdOrderByCreatedAtDesc(classroomId)
    }

    fun findAllByClassroomId(classroomId: Long): List<ClassroomAnnouncement> {
        return classroomAnnouncementRepository.findAllByClassroomId(classroomId)
    }

    fun findById(classroomAnnouncementId: Long) : ClassroomAnnouncement {
        return findAnnouncement {
            classroomAnnouncementRepository.findByIdOrNull(classroomAnnouncementId)
        }
    }

    private fun findAnnouncement(function: () -> ClassroomAnnouncement?): ClassroomAnnouncement {
        return function() ?: throw AnnouncementException.AnnouncementNotFound()
    }
}