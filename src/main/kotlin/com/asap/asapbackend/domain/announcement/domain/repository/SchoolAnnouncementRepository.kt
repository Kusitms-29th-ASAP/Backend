package com.asap.asapbackend.domain.announcement.domain.repository

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import org.springframework.data.jpa.repository.JpaRepository

interface SchoolAnnouncementRepository : JpaRepository<SchoolAnnouncement, Long> {
}