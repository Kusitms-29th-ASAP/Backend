package com.asap.asapbackend.infrastructure.jpa.timetable.repository

import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import com.asap.asapbackend.infrastructure.jpa.timetable.entity.TranslatedSubjectEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TranslatedSubjectJpaRepository: JpaRepository<TranslatedSubjectEntity, MultiLanguageId> {
}