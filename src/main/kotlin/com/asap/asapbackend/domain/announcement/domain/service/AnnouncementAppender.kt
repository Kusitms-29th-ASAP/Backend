package com.asap.asapbackend.domain.announcement.domain.service

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import com.asap.asapbackend.domain.announcement.domain.repository.OfficeEducationAnnouncementJdbcRepository
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementJdbcRepository
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementRepository
import org.springframework.stereotype.Service

@Service
class AnnouncementAppender(
    private val schoolAnnouncementJdbcRepository: SchoolAnnouncementJdbcRepository,
    private val officeEducationAnnouncementJdbcRepository: OfficeEducationAnnouncementJdbcRepository
) {

    fun addSchoolAnnouncements(schoolAnnouncement: Set<SchoolAnnouncement>) {
        schoolAnnouncementJdbcRepository.insertBatch(schoolAnnouncement)
    }

    fun addEducationOfficeAnnouncements(educationOfficeAnnouncement: Set<EducationOfficeAnnouncement>) {
        officeEducationAnnouncementJdbcRepository.insertBatch(educationOfficeAnnouncement)
    }

}