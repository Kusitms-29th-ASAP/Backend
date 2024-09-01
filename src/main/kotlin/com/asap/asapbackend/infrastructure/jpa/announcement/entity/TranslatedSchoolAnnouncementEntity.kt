package com.asap.asapbackend.infrastructure.jpa.announcement.entity

import com.asap.asapbackend.global.vo.Language
import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "translated_school_announcement")
class TranslatedSchoolAnnouncementEntity(
    schoolAnnouncementId: Long,
    language: Language,
    title: String,
    summaries: List<String>,
    keywords: List<String>
) {

    @EmbeddedId
    val id: MultiLanguageId = MultiLanguageId(schoolAnnouncementId, language)

    val title: String = title
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(
        nullable = false,
        columnDefinition = "json"
    )
    val summaries: List<String> = summaries
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(
        nullable = false,
        columnDefinition = "json"
    )
    val keywords: List<String> = keywords
}