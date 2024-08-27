package com.asap.asapbackend.infrastructure.jpa.classroom.entity

import com.asap.asapbackend.domain.classroom.domain.vo.AnnouncementDescription
import com.asap.asapbackend.global.vo.Language
import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
class TranslatedClassroomAnnouncementEntity(
    classroomAnnouncementId: Long,
    language: Language,
    descriptions: List<AnnouncementDescription>
) {

    @EmbeddedId
    val id: MultiLanguageId = MultiLanguageId(classroomAnnouncementId, language)

    @Column(
        nullable = false,
        columnDefinition = "json"
    )
    @JdbcTypeCode(SqlTypes.JSON)
    val descriptions: List<AnnouncementDescription> = descriptions
}