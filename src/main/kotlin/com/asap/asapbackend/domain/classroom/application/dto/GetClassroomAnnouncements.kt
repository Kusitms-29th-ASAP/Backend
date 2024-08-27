package com.asap.asapbackend.domain.classroom.application.dto


import com.asap.asapbackend.domain.classroom.domain.model.ClassroomAnnouncement
import com.asap.asapbackend.domain.classroom.domain.vo.AnnouncementDescription
import java.time.LocalDate

class GetClassroomAnnouncements {
    data class Response(
        val teacherName: String,
        val announcements : List<AnnouncementInfo>
    )

    data class AnnouncementInfo(
        val descriptions: List<AnnouncementDescription>,
        val writeDate : LocalDate
    )

    fun toAnnouncementInfo(classroomAnnouncements: List<ClassroomAnnouncement>) : List<AnnouncementInfo> {
        return classroomAnnouncements.map {
            AnnouncementInfo(it.descriptions,it.writeDate)
        }
    }
}