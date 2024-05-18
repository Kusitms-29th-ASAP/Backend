package com.asap.asapbackend.domain.announcement.domain.repository

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SchoolAnnouncementRepository : JpaRepository<SchoolAnnouncement, Long> {
    @Query("SELECT COALESCE(MAX(idx), 0) FROM SchoolAnnouncement")
    fun findLastIndex(): Int
}