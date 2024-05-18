package com.asap.asapbackend.domain.announcement.domain.service

import com.asap.asapbackend.domain.announcement.domain.repository.EducationOfficeAnnouncementRepository
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementRepository
import org.springframework.stereotype.Service

@Service
class AnnouncementReader(
    private val schoolAnnouncementRepository: SchoolAnnouncementRepository,
    private val educationOfficeAnnouncementRepository: EducationOfficeAnnouncementRepository
) {

    fun getLastOfficeEducationAnnouncementId(): Int {
        return educationOfficeAnnouncementRepository.findLastIndex()
    }
}