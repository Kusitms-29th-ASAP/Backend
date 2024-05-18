package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.domain.classroom.domain.exception.ClassroomAnnouncementException
import com.asap.asapbackend.domain.classroom.domain.model.ClassroomAnnouncement
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomAnnouncementRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ClassroomAnnouncementReader(
    private val classroomAnnouncementRepository: ClassroomAnnouncementRepository
) {
    fun findRecentClassroomAnnouncementByClassroomIdOrNull(classroomId: Long): ClassroomAnnouncement? {
        return classroomAnnouncementRepository.findTopByClassroomIdOrderByCreatedAtDesc(classroomId)
    }

    fun findAllByClassroomId(classroomId: Long): List<ClassroomAnnouncement> {
        return classroomAnnouncementRepository.findAllByClassroomId(classroomId)
    }

    fun findById(classroomAnnouncementId: Long) : ClassroomAnnouncement {
        return findClassroomAnnouncement {
            classroomAnnouncementRepository.findByIdOrNull(classroomAnnouncementId)
        }
    }

    private fun findClassroomAnnouncement(function: () -> ClassroomAnnouncement?): ClassroomAnnouncement {
        return function() ?: throw ClassroomAnnouncementException.ClassroomAnnouncementNotFound()
    }
}