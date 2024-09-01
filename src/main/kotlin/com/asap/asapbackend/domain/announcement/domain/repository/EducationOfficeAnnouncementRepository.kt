package com.asap.asapbackend.domain.announcement.domain.repository

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface EducationOfficeAnnouncementRepository{
    fun findLastIndex(): Int

    fun insertBatch(announcements: Set<EducationOfficeAnnouncement>): Set<EducationOfficeAnnouncement>

    fun findAll(pageable: Pageable): Page<EducationOfficeAnnouncement>

    fun findByIdOrNull(id: Long): EducationOfficeAnnouncement?
}