package com.asap.asapbackend.infrastructure.jpa.announcement.repository

import com.asap.asapbackend.infrastructure.jpa.announcement.entity.EducationOfficeAnnouncementEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EducationAnnouncementJpaRepository : JpaRepository<EducationOfficeAnnouncementEntity, Long>{
    @Query("SELECT COALESCE(MAX(idx), 0) FROM EducationOfficeAnnouncementEntity ")
    fun findLastIndex(): Int
}