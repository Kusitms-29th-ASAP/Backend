package com.asap.asapbackend.domain.classroom.application.dto

import com.asap.asapbackend.domain.classroom.domain.vo.AnnouncementDescription

class GetTodayAnnouncement {
    data class Response(
        val descriptions: List<AnnouncementDescription>
    )
}