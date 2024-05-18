package com.asap.asapbackend.domain.announcement.domain.model

import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
class EducationOfficeAnnouncement(
    idx: Int,
    title: String,
    imageUrls: List<String>,
    summaries: List<String>
) : BaseDateEntity() {

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
}