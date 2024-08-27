package com.asap.asapbackend.infrastructure.jpa.classroom.repository

import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import com.asap.asapbackend.infrastructure.jpa.classroom.entity.TranslatedClassroomAnnouncementEntity
import org.springframework.data.jpa.repository.JpaRepository


interface TranslatedClassroomAnnouncementJpaRepository :
    JpaRepository<TranslatedClassroomAnnouncementEntity, MultiLanguageId> {
}