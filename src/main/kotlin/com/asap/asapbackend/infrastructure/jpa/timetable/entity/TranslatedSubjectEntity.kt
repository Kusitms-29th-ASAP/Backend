package com.asap.asapbackend.infrastructure.jpa.timetable.entity

import com.asap.asapbackend.global.vo.Language
import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(
    name = "translated_subject"
)
class TranslatedSubjectEntity(
    subjectId: Long,
    language: Language,
    name: String
) {
    @EmbeddedId
    val id: MultiLanguageId = MultiLanguageId(subjectId, language)

    val name: String = name
}