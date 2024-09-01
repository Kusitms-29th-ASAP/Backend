package com.asap.asapbackend.infrastructure.jpa.announcement.entity

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncementPage
import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime

@Entity
@Table(name = "school_announcement")
class SchoolAnnouncementEntity(
    id: Long,
    schoolAnnouncementPageId: Long,
    index: Int,
    title: String,
    imageUrls: List<String>,
    summaries: List<String>,
    keywords: List<String>,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = id

    @Column(name = "school_announcement_page_id", nullable = false)
    val schoolAnnouncementPageId: Long = schoolAnnouncementPageId

    val idx: Int = index
    var title: String = title

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
    var summaries: List<String> = summaries

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(
        nullable = false,
        columnDefinition = "json"
    )
    var keywords: List<String> = keywords

    val createdAt: LocalDateTime = createdAt
    val updatedAt: LocalDateTime = updatedAt

    fun changeTitle(title: String) {
        this.title = title
    }

    fun changeSummaries(summaries: List<String>) {
        this.summaries = summaries
    }

    fun changeKeywords(keywords: List<String>) {
        this.keywords = keywords
    }
}