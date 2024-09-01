package com.asap.asapbackend.domain.announcement.domain.repository

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SchoolAnnouncementRepository {

    fun findLastIndex(schoolId: Long): Int

    fun findAllByClassroomId(classroomId: Long, pageable: Pageable): Page<SchoolAnnouncement>

    fun insertBatch(announcements: Set<SchoolAnnouncement>): Set<SchoolAnnouncement>

    fun findByIdOrNull(id: Long): SchoolAnnouncement?
}