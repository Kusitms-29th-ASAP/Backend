package com.asap.asapbackend.domain.announcement.application.dto

import com.asap.asapbackend.domain.announcement.domain.vo.AnnouncementCategory

class UpdateEducationOfficeAnnouncementCategory {

    data class Request(
        val category: AnnouncementCategory
    )
}