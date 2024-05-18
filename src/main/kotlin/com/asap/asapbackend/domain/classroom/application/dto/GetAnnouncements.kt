package com.asap.asapbackend.domain.classroom.application.dto


import com.asap.asapbackend.domain.classroom.domain.model.Announcement
import com.asap.asapbackend.domain.classroom.domain.vo.AnnouncementDescription
import java.time.LocalDate

class GetAnnouncements {
    data class Response(
        val teacher: String,
        val announcements : List<AnnouncementInfo>
    )

    data class AnnouncementInfo(
        val descriptions: List<AnnouncementDescription>,
        val writeDate : LocalDate
    )

    fun toAnnouncementInfo(announcements: List<Announcement>) : List<AnnouncementInfo> {
        return announcements.map {
            AnnouncementInfo(it.descriptions,it.getWriteDate())
        }
    }
}