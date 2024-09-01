package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.domain.classroom.domain.model.ClassroomAnnouncement
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomAnnouncementRepository
import com.asap.asapbackend.domain.classroom.event.ClassroomAnnouncementCreateEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class ClassroomAnnouncementAppender(
    private val classroomAnnouncementRepository: ClassroomAnnouncementRepository,
    private val eventPublisher: ApplicationEventPublisher
) {

    fun append(classroomAnnouncement: ClassroomAnnouncement): ClassroomAnnouncement {
        val savedAnnouncement= classroomAnnouncementRepository.save(classroomAnnouncement)
        eventPublisher.publishEvent(ClassroomAnnouncementCreateEvent(savedAnnouncement))
        return savedAnnouncement
    }
}