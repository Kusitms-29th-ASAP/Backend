package com.asap.asapbackend.domain.announcement.domain.model

import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
class OfficeEducationAnnouncement(
    title: String,
    content: String,
    imageUrls: List<String>
) : BaseDateEntity() {

    val title: String = title
    val content: String = content


    @Column(
        nullable = false,
        columnDefinition = "json"
    )
    @JdbcTypeCode(SqlTypes.JSON)
    val imageUrls: List<String> = imageUrls
}