package com.asap.asapbackend.domain.announcement.application.dto

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import com.asap.asapbackend.domain.announcement.domain.vo.AnnouncementCategory
import org.springframework.data.domain.Page

class GetSimpleEducationOfficeAnnouncementPage {

    data class Request(
        val size: Int,
        val page: Int
    )

    data class Response(
        val schoolAnnouncements: List<SimpleSchoolAnnouncement>,
        val totalPage : Int,
        val totalElements : Long
    )

    data class SimpleSchoolAnnouncement(
        val id: Long,
        val title: String,
        val category: AnnouncementCategory
    )

    companion object {
        fun convertEducationOfficeAnnouncementToResponse(
            educationOfficeAnnouncements: Page<EducationOfficeAnnouncement>,
            categoryQuery: (Long) -> AnnouncementCategory
        ): Response {
            return Response(
                educationOfficeAnnouncements.content.map {
                    SimpleSchoolAnnouncement(
                        it.id,
                        it.title,
                        categoryQuery(it.id)
                    )
                },
                educationOfficeAnnouncements.totalPages,
                educationOfficeAnnouncements.totalElements
            )
        }
    }
}