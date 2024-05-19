package com.asap.asapbackend.domain.announcement.domain.repository

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncementCategory
import org.springframework.data.jpa.repository.JpaRepository

interface SchoolAnnouncementCategoryRepository:JpaRepository<SchoolAnnouncementCategory, Long> {
    fun findBySchoolAnnouncementId(schoolAnnouncementId: Long): SchoolAnnouncementCategory?


    fun findBySchoolAnnouncementIdAndClassroomId(schoolAnnouncementId: Long, classroomId: Long): SchoolAnnouncementCategory?
}