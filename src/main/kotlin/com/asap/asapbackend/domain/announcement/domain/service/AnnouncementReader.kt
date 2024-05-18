package com.asap.asapbackend.domain.announcement.domain.service

import com.asap.asapbackend.domain.announcement.domain.repository.OfficeEducationAnnouncementRepository
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementRepository
import org.springframework.stereotype.Service

@Service
class AnnouncementReader(
    private val schoolAnnouncementRepository: SchoolAnnouncementRepository,
    private val officeEducationAnnouncementRepository: OfficeEducationAnnouncementRepository
) {
    fun getLastSchoolAnnouncementId(): Int {
        return schoolAnnouncementRepository.findLastIndex()
    }

    fun getLastOfficeEducationAnnouncementId(): Int {
        return officeEducationAnnouncementRepository.findLastIndex()
    }
}