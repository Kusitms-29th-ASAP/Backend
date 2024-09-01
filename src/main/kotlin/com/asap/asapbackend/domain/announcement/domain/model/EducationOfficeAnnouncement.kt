package com.asap.asapbackend.domain.announcement.domain.model

import jakarta.persistence.Column
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime

class EducationOfficeAnnouncement(
    id: Long = 0L,
    idx: Int,
    title: String,
    imageUrls: List<String>,
    summaries: List<String>,
    keywords: List<String>,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now()
){

    val id: Long = id

    val idx: Int = idx
    val title: String = title


    @Column(
        nullable = false,
        columnDefinition = "json"
    )
    @JdbcTypeCode(SqlTypes.JSON)
    val imageUrls: List<String> = imageUrls


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

    val createdAt: LocalDateTime = createdAt
    val updatedAt: LocalDateTime = updatedAt
}