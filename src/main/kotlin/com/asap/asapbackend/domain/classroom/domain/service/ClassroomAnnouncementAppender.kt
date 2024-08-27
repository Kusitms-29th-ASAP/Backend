package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.domain.classroom.domain.model.ClassroomAnnouncement
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomAnnouncementRepository
import org.springframework.stereotype.Service

@Service
class ClassroomAnnouncementAppender(
    private val classroomAnnouncementRepository: ClassroomAnnouncementRepository
) {

    fun append(classroomAnnouncement: ClassroomAnnouncement): ClassroomAnnouncement {
        return classroomAnnouncementRepository.save(classroomAnnouncement)
    }
}