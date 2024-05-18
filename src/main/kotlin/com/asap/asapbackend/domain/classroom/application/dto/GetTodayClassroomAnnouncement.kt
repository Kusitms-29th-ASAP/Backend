package com.asap.asapbackend.domain.classroom.application.dto

import com.asap.asapbackend.domain.classroom.domain.model.ClassroomAnnouncement
import com.asap.asapbackend.domain.classroom.domain.vo.AnnouncementDescription

class GetTodayClassroomAnnouncement {
    data class Response(
        val announcementId : Long?,
        val descriptions: List<AnnouncementDescription>
    )

    companion object {
        fun convertClassAnnouncementToResponse(classroomAnnouncementQuery: () -> ClassroomAnnouncement?): Response{
            val announcement = classroomAnnouncementQuery()
            val descriptions = announcement?.getSubListFromDescription(0,3) ?: emptyList()
            return  Response(announcement?.id, descriptions)
        }
    }
}