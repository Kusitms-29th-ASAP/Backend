package com.asap.asapbackend.domain.announcement.domain.repository

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncementCategory
import org.springframework.data.jpa.repository.JpaRepository

interface EducationOfficeAnnouncementCategoryRepository: JpaRepository<EducationOfficeAnnouncementCategory, Long> {
    fun findByEducationOfficeAnnouncementIdAndClassroomId(educationOfficeAnnouncementId: Long, classroomId: Long): EducationOfficeAnnouncementCategory?
}