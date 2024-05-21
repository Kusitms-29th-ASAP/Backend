package com.asap.asapbackend.domain.announcement.application.dto

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import com.asap.asapbackend.domain.announcement.domain.vo.AnnouncementCategory
import org.springframework.data.domain.Slice
import java.time.LocalDate

class GetSchoolAnnouncementSlice {

    data class Request(
        val size: Int,
        val page: Int
    )

    data class Response(
        val schoolAnnouncementInfos: List<SchoolAnnouncementInfo>,
        val hasNext: Boolean
    )

    data class SchoolAnnouncementInfo(
        val announcementId: Long,
        val title: String,
        val category: AnnouncementCategory,
        val uploadDate: LocalDate,
        val summary: List<String>,
    ) {
        val isNew: Boolean = (uploadDate == LocalDate.now())
    }

    companion object {
        fun convertSchoolAnnouncementToResponse(schoolAnnouncements: Slice<SchoolAnnouncement>, categoryQuery: (Long) -> AnnouncementCategory) = Response(
            schoolAnnouncementInfos = schoolAnnouncements.map {
                SchoolAnnouncementInfo(
                    announcementId = it.id,
                    title = it.title,
                    category = categoryQuery(it.id),
                    uploadDate = it.createdAt.toLocalDate(),
                    summary = it.summaries
                )
            }.content,
            hasNext = schoolAnnouncements.hasNext()
        )

    }
}