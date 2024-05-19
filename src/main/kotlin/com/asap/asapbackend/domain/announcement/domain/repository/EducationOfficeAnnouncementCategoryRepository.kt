package com.asap.asapbackend.domain.announcement.domain.repository

import com.asap.asapbackend.domain.announcement.domain.model.EducationAnnouncementCategory
import org.springframework.data.jpa.repository.JpaRepository

interface EducationOfficeAnnouncementCategoryRepository: JpaRepository<EducationAnnouncementCategory, Long> {
    fun findByEducationOfficeAnnouncementId(educationOfficeAnnouncementId: Long): EducationAnnouncementCategory?

    fun findByEducationOfficeAnnouncementIdAndClassroomId(educationOfficeAnnouncementId: Long, classroomId: Long): EducationAnnouncementCategory?
}