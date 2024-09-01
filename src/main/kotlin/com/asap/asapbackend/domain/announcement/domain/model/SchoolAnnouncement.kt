package com.asap.asapbackend.domain.announcement.domain.model

import java.time.LocalDateTime

class SchoolAnnouncement(
    id: Long = 0,
    schoolAnnouncementPageId: Long,
    index: Int,
    title: String,
    imageUrls: List<String>,
    summaries : List<String>,
    keywords: List<String>,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now()
){

    val id: Long = id
    val schoolAnnouncementPageId: Long = schoolAnnouncementPageId
    val idx: Int = index
    val title: String = title
    val imageUrls: List<String> = imageUrls

    val summaries: List<String> = summaries

    val keywords: List<String> = keywords

    val createdAt: LocalDateTime = createdAt
    val updatedAt: LocalDateTime = updatedAt
}