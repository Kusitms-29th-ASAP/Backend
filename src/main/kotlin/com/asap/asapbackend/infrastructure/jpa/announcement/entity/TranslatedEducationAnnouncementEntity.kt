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
@Table(name = "translated_education_announcement")
class TranslatedEducationAnnouncementEntity(
    announcementId: Long,
    language: Language,
    title: String,
    summaries: List<String>,
    keywords: List<String>
) {

    @EmbeddedId
    val id: MultiLanguageId = MultiLanguageId(announcementId, language)

    val title: String = title

    @Column(
        nullable = false,
        columnDefinition = "json"
    )
    @JdbcTypeCode(SqlTypes.JSON)
    val summaries: List<String> = summaries

    @Column(
        nullable = false,
        columnDefinition = "json"
    )
    @JdbcTypeCode(SqlTypes.JSON)
    val keywords: List<String> = keywords


}