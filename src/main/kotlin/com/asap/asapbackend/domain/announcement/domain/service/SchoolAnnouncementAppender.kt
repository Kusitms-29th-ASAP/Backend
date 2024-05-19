package com.asap.asapbackend.domain.announcement.domain.service

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncementCategory
import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncementCategory
import com.asap.asapbackend.domain.announcement.domain.repository.EducationOfficeAnnouncementCategoryRepository
import com.asap.asapbackend.domain.announcement.domain.repository.EducationOfficeAnnouncementJdbcRepository
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementCategoryRepository
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementJdbcRepository
import org.springframework.stereotype.Service

@Service
class SchoolAnnouncementAppender(
    private val schoolAnnouncementJdbcRepository: SchoolAnnouncementJdbcRepository,
    private val educationOfficeAnnouncementJdbcRepository: EducationOfficeAnnouncementJdbcRepository,
    private val schoolAnnouncementCategoryRepository: SchoolAnnouncementCategoryRepository,
    private val educationOfficeAnnouncementCategoryRepository: EducationOfficeAnnouncementCategoryRepository
) {

    fun addSchoolAnnouncements(schoolAnnouncement: Set<SchoolAnnouncement>) {
        schoolAnnouncementJdbcRepository.insertBatch(schoolAnnouncement)
    }

    fun addEducationOfficeAnnouncements(educationOfficeAnnouncement: Set<EducationOfficeAnnouncement>) {
        educationOfficeAnnouncementJdbcRepository.insertBatch(educationOfficeAnnouncement)
    }

    fun addSchoolAnnouncementCategory(category: SchoolAnnouncementCategory){
        schoolAnnouncementCategoryRepository.save(category)
    }

    fun addEducationAnnouncementCategory(category: EducationOfficeAnnouncementCategory){
        educationOfficeAnnouncementCategoryRepository.save(category)
    }

}