package com.asap.asapbackend.infrastructure.jpa.announcement.entity

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime

@Entity
@Table(name = "education_office_announcement")
class EducationOfficeAnnouncementEntity(
    id: Long,
    idx: Int,
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
    val idx: Int = idx
    var title: String = title

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
    var summaries: List<String> = summaries

    @Column(
        nullable = false,
        columnDefinition = "json"
    )
    @JdbcTypeCode(SqlTypes.JSON)
    var keywords: List<String> = keywords

    val createdAt: LocalDateTime = createdAt
    val updatedAt: LocalDateTime = updatedAt


    fun changeSummaries(summaries: List<String>){
        if(summaries.isNotEmpty()){
            this.summaries = summaries
        }
    }

    fun changeKeywords(keywords: List<String>){
        if(keywords.isNotEmpty()){
            this.keywords = keywords
        }
    }

    fun changeTitle(title: String){
        if(title.isNotEmpty()){
            this.title = title
        }
    }


}