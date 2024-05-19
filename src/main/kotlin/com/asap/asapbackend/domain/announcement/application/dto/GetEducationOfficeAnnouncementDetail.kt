package com.asap.asapbackend.domain.announcement.application.dto

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import com.asap.asapbackend.domain.announcement.domain.vo.AnnouncementCategory
import java.time.LocalDate

class GetEducationOfficeAnnouncementDetail {

    data class Request(
        val announcementId: Long
    )

    data class Response(
        val title: String,
        val category: AnnouncementCategory,
        val highlight: AnnouncementHighlight,
        val uploadDate: LocalDate,
        val imageUrls: List<String>
    )


    data class AnnouncementHighlight(
        val keywords: List<String>,
        val summaries: List<String>
    )

    companion object{
        fun convertEducationOfficeAnnouncementToResponse(educationOfficeAnnouncement: EducationOfficeAnnouncement, categoryQuery: (Long) -> AnnouncementCategory) = Response(
            highlight = AnnouncementHighlight(
                keywords = educationOfficeAnnouncement.keywords,
                summaries = educationOfficeAnnouncement.summaries
            ),
            title = educationOfficeAnnouncement.title,
            category = categoryQuery(educationOfficeAnnouncement.id),
            uploadDate = educationOfficeAnnouncement.createdAt.toLocalDate(),
            imageUrls = educationOfficeAnnouncement.imageUrls
        )
    }
}