package com.asap.asapbackend.domain.announcement.domain.model

import com.asap.asapbackend.domain.announcement.domain.vo.AnnouncementCategory
import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.global.domain.BaseDateEntity
import com.asap.asapbackend.global.exception.validateProperty
import jakarta.persistence.*

@Entity
class EducationOfficeAnnouncementCategory(
    classroom: Classroom,
    educationOfficeAnnouncement: EducationOfficeAnnouncement,
    announcementCategory: AnnouncementCategory
): BaseDateEntity(){

    @ManyToOne(fetch = FetchType.LAZY)
    val classroom: Classroom = classroom
    val educationOfficeAnnouncementId: Long = educationOfficeAnnouncement.id

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        columnDefinition = "varchar(255)"
    )
    var category: AnnouncementCategory = announcementCategory
        protected set

    fun updateCategory(category: AnnouncementCategory) {
        validateProperty(category != AnnouncementCategory.NONE) { "카테고리를 선택해야 합니다." }
        this.category = category
    }
}