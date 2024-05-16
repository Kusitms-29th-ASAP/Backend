package com.asap.asapbackend.domain.classroom.application.dto


import com.asap.asapbackend.domain.classroom.domain.vo.AnnouncementDescription
import java.time.LocalDate

class GetAnnouncements {
    data class Response(
        val teacher: String,
        val announcements : List<Announcement>
    )

    data class Announcement(
        val descriptions: List<AnnouncementDescription>,
        val writeDate: LocalDate,
    )
}