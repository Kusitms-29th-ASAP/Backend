package com.asap.asapbackend.domain.announcement.application.dto

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import com.asap.asapbackend.domain.announcement.domain.vo.AnnouncementCategory
import java.time.LocalDate

class GetSchoolAnnouncementDetail {
    data class Request(
        val announcementId: Long
    )

    data class Response(
        val title: String,
        val category: AnnouncementCategory,
        val highlight: AnnouncementHighlight,

        val uploadDate: LocalDate,
        val imageUrls: List<String>
    ){
        val isNew = (uploadDate == LocalDate.now())
    }


    data class AnnouncementHighlight(
        val keywords: List<String>,
        val summaries: List<String>
    )

    companion object{
        fun convertSchoolAnnouncementToResponse(schoolAnnouncement: SchoolAnnouncement, categoryQuery: (Long) -> AnnouncementCategory) = Response(
            highlight = AnnouncementHighlight(
                keywords = schoolAnnouncement.keywords,
                summaries = schoolAnnouncement.summaries
            ),
            title = schoolAnnouncement.title,
            category = categoryQuery(schoolAnnouncement.id),
            uploadDate = schoolAnnouncement.createdAt.toLocalDate(),
            imageUrls = schoolAnnouncement.imageUrls
        )
    }
}