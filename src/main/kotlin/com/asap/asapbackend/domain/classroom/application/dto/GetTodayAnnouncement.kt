package com.asap.asapbackend.domain.classroom.application.dto

import com.asap.asapbackend.domain.classroom.domain.model.Announcement
import com.asap.asapbackend.domain.classroom.domain.vo.AnnouncementDescription

class GetTodayAnnouncement {
    data class Response(
        val announcementId : Long?,
        val descriptions: List<AnnouncementDescription>
    )

    companion object {
        fun convertClassAnnouncementToResponse(announcementQuery: () -> Announcement?): Response{
            val announcement = announcementQuery()
            val descriptions = announcement?.descriptions ?: emptyList()
            return  Response(announcement?.id, descriptions)
        }
    }
}