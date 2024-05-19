package com.asap.asapbackend.domain.announcement.domain.model

import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
class SchoolAnnouncement(
    schoolAnnouncementPage: SchoolAnnouncementPage,
    index: Int,
    title: String,
    imageUrls: List<String>,
    summaries : List<String>,
    keywords: List<String>
) : BaseDateEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    val schoolAnnouncementPage: SchoolAnnouncementPage = schoolAnnouncementPage

    val idx: Int = index
    val title: String = title

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(
        nullable = false,
        columnDefinition = "json"
    )
    val imageUrls: List<String> = imageUrls

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