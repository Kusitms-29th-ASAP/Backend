package com.asap.asapbackend.domain.announcement.domain.repository

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EducationOfficeAnnouncementRepository : JpaRepository<EducationOfficeAnnouncement, Long> {
    @Query("SELECT COALESCE(MAX(idx), 0) FROM EducationOfficeAnnouncement")
    fun findLastIndex(): Int
}