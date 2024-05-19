package com.asap.asapbackend.domain.announcement.application.dto

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import com.asap.asapbackend.domain.announcement.domain.vo.AnnouncementCategory
import org.springframework.data.domain.Slice
import java.time.LocalDate

class GetEducationOfficeAnnouncementSlice {

    data class Request(
        val size: Int,
        val page: Int
    )

    data class Response(
        val educationOfficeAnnouncementInfos: List<EducationOfficeAnnouncementInfo>,
        val hasNext: Boolean = false
    )

    data class EducationOfficeAnnouncementInfo(
        val announcementId: Long,
        val title: String,
        val category: AnnouncementCategory,
        val uploadDate: LocalDate,
        val summary: List<String>
    ){
        val isNew: Boolean = (uploadDate == LocalDate.now())
    }

    companion object {
        fun convertEducationOfficeAnnouncementToResponse(
            educationOfficeAnnouncements: Slice<EducationOfficeAnnouncement>,
            categoryQuery: (Long) -> AnnouncementCategory) = Response(
            educationOfficeAnnouncementInfos = educationOfficeAnnouncements.map {
                EducationOfficeAnnouncementInfo(
                    announcementId = it.id,
                    title = it.title,
                    category = categoryQuery(it.id),
                    uploadDate = it.createdAt.toLocalDate(),
                    summary = it.summaries
                )
            }.content,
            hasNext = educationOfficeAnnouncements.hasNext()
        )
    }

}