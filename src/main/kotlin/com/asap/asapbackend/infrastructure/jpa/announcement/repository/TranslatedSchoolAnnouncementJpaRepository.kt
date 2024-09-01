package com.asap.asapbackend.infrastructure.jpa.announcement.repository

import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import com.asap.asapbackend.infrastructure.jpa.announcement.entity.TranslatedSchoolAnnouncementEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TranslatedSchoolAnnouncementJpaRepository: JpaRepository<TranslatedSchoolAnnouncementEntity, MultiLanguageId> {
}