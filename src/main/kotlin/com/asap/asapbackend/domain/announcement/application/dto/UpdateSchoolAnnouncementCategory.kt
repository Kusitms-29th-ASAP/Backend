package com.asap.asapbackend.domain.announcement.application.dto

import com.asap.asapbackend.domain.announcement.domain.vo.AnnouncementCategory

class UpdateSchoolAnnouncementCategory {

    data class Request(
        val category: AnnouncementCategory
    )


}