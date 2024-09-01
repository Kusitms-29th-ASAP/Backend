package com.asap.asapbackend.infrastructure.jpa.announcement.repository

import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import com.asap.asapbackend.infrastructure.jpa.announcement.entity.TranslatedEducationAnnouncementEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TranslatedEducationAnnouncementJpaRepository: JpaRepository<TranslatedEducationAnnouncementEntity, MultiLanguageId> {
}