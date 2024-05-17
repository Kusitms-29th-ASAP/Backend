package com.asap.asapbackend.domain.announcement.domain.repository

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncementPage
import org.springframework.data.jpa.repository.JpaRepository

interface SchoolAnnouncementPageRepository : JpaRepository<SchoolAnnouncementPage, Long>{
}