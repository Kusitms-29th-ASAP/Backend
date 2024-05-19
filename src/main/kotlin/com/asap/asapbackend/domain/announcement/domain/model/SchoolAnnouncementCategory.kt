package com.asap.asapbackend.domain.announcement.domain.model

import com.asap.asapbackend.domain.announcement.domain.vo.AnnouncementCategory
import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.*

@Entity
class SchoolAnnouncementCategory(
    classroom: Classroom,
    schoolAnnouncement: SchoolAnnouncement,
    announcementCategory: AnnouncementCategory
): BaseDateEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    val classroom: Classroom = classroom

    @ManyToOne(fetch = FetchType.LAZY)
    val schoolAnnouncement: SchoolAnnouncement = schoolAnnouncement

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        columnDefinition = "varchar(255)"
    )
    val category: AnnouncementCategory = announcementCategory
}