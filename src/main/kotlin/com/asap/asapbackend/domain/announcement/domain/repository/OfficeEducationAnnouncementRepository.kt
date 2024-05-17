package com.asap.asapbackend.domain.announcement.domain.repository

import com.asap.asapbackend.domain.announcement.domain.model.OfficeEducationAnnouncement
import org.springframework.data.jpa.repository.JpaRepository

interface OfficeEducationAnnouncementRepository : JpaRepository<OfficeEducationAnnouncement, Long> {
}