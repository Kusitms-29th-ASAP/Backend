package com.asap.asapbackend.domain.classroom.application.dto

import com.asap.asapbackend.domain.classroom.domain.vo.AnnouncementDescription
import java.time.LocalDate

class GetAnnouncementDetail {
    data class Response(
        val teacherName: String,
        val writeDate : LocalDate,
        val descriptions: List<AnnouncementDescription>
    )
}