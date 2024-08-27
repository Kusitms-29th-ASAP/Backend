package com.asap.asapbackend.domain.classroom.domain.model

import com.asap.asapbackend.domain.classroom.domain.vo.AnnouncementDescription
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.min

class ClassroomAnnouncement(
    id: Long = 0,
    descriptions: List<AnnouncementDescription>,
    classroomId: Long,
    teacherId: Long,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now()
) {
    val id: Long = id

    val descriptions: List<AnnouncementDescription> = descriptions

    val classroomId: Long = classroomId

    val teacherId : Long = teacherId

    val createdAt: LocalDateTime = createdAt
    val updatedAt: LocalDateTime = updatedAt

    val writeDate: LocalDate = this.createdAt.toLocalDate()

    fun getSubListFromDescription(fromIndex: Int, toIndex: Int) : List<AnnouncementDescription>{
        return this.descriptions.subList(fromIndex, min(toIndex, this.descriptions.size))
    }
}