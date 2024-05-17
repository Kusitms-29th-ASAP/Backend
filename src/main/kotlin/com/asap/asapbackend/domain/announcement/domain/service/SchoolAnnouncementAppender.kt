package com.asap.asapbackend.domain.announcement.domain.service

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementJdbcRepository
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementRepository
import org.springframework.stereotype.Service

@Service
class SchoolAnnouncementAppender(
    private val schoolAnnouncementRepository: SchoolAnnouncementRepository,
    private val schoolAnnouncementJdbcRepository: SchoolAnnouncementJdbcRepository
) {

    fun addAnnouncements(schoolAnnouncement: Set<SchoolAnnouncement>) {
        schoolAnnouncementJdbcRepository.insertBatch(schoolAnnouncement)
    }

}