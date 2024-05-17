package com.asap.asapbackend.domain.announcement.domain.model

import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class SchoolAnnouncementPage(
    school: School,
    schoolAnnouncementPageUrl: String,
) : BaseDateEntity(){

    @ManyToOne(fetch = FetchType.LAZY)
    val school: School = school

    val schoolAnnouncementPageUrl: String = schoolAnnouncementPageUrl
}