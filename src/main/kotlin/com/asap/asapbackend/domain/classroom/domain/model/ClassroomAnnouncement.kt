package com.asap.asapbackend.domain.classroom.domain.model

import com.asap.asapbackend.domain.classroom.domain.vo.AnnouncementDescription
import com.asap.asapbackend.domain.teacher.domain.model.Teacher
import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import kotlin.math.min

@Entity
class ClassroomAnnouncement(
    descriptions: List<AnnouncementDescription>,
    classroom: Classroom,
    teacher: Teacher
) : BaseDateEntity() {


    @Column(
        nullable = false,
        columnDefinition = "json"
    )
    @JdbcTypeCode(SqlTypes.JSON)
    val descriptions: List<AnnouncementDescription> = descriptions

    @ManyToOne(fetch = FetchType.LAZY)
    val classroom: Classroom = classroom

    @ManyToOne(fetch = FetchType.LAZY)
    val teacher: Teacher = teacher

    fun getWriteDate() = this.createdAt.toLocalDate()

    fun getSubListFromDescription(fromIndex: Int, toIndex: Int) : List<AnnouncementDescription>{
        return this.descriptions.subList(fromIndex, min(toIndex, this.descriptions.size))
    }
}