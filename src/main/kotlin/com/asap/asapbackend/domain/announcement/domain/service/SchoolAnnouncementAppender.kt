package com.asap.asapbackend.domain.announcement.domain.service

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import com.asap.asapbackend.domain.announcement.domain.repository.EducationOfficeAnnouncementRepository
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementRepository
import com.asap.asapbackend.domain.announcement.event.MultiEducationOfficeAnnouncementCreateEvent
import com.asap.asapbackend.domain.announcement.event.MultiSchoolAnnouncementCreateEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class SchoolAnnouncementAppender(
    private val educationOfficeAnnouncementRepository: EducationOfficeAnnouncementRepository,
    private val schoolAnnouncementRepository: SchoolAnnouncementRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun addSchoolAnnouncements(schoolAnnouncement: Set<SchoolAnnouncement>) {
        val savedAnnouncement = schoolAnnouncementRepository.insertBatch(schoolAnnouncement)
        print(savedAnnouncement)
        applicationEventPublisher.publishEvent(MultiSchoolAnnouncementCreateEvent(savedAnnouncement))
    }

    fun addEducationOfficeAnnouncements(educationOfficeAnnouncement: Set<EducationOfficeAnnouncement>) {
        val savedEducations = educationOfficeAnnouncementRepository.insertBatch(educationOfficeAnnouncement)
        applicationEventPublisher.publishEvent(MultiEducationOfficeAnnouncementCreateEvent(savedEducations))
    }

}