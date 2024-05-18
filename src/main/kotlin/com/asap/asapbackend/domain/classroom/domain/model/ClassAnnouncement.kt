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
import java.time.LocalDate

@Entity
class ClassAnnouncement(
    descriptions: List<AnnouncementDescription>,
    writeDate: LocalDate,
    classroom: Classroom,
    teacher: Teacher
) : BaseDateEntity() {


    @Column(
        nullable = false,
        columnDefinition = "json"
    )
    @JdbcTypeCode(SqlTypes.JSON)
    val descriptions: List<AnnouncementDescription> = descriptions

    val writeDate: LocalDate = writeDate

    @ManyToOne(fetch = FetchType.LAZY)
    val classroom: Classroom = classroom

    @ManyToOne(fetch = FetchType.LAZY)
    val teacher: Teacher = teacher
}