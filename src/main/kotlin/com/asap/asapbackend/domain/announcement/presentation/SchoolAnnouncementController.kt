package com.asap.asapbackend.domain.announcement.presentation

import com.asap.asapbackend.domain.announcement.application.SchoolAnnouncementService
import com.asap.asapbackend.domain.announcement.application.dto.GetEducationOfficeAnnouncementDetail
import com.asap.asapbackend.domain.announcement.application.dto.GetEducationOfficeAnnouncementSlice
import com.asap.asapbackend.domain.announcement.application.dto.GetSchoolAnnouncementDetail
import com.asap.asapbackend.domain.announcement.application.dto.GetSchoolAnnouncementSlice
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
class SchoolAnnouncementController(
    private val announcementService: SchoolAnnouncementService
) {


    @GetMapping(AnnouncementApi.V1.SCHOOL_ANNOUNCEMENT)
    fun getSchoolAnnouncement(
        @RequestParam size: Int,
        @RequestParam page: Int
    ): GetSchoolAnnouncementSlice.Response {
        return announcementService.getSchoolAnnouncementSlice(GetSchoolAnnouncementSlice.Request(size, page))
    }

    @GetMapping(AnnouncementApi.V1.SCHOOL_ANNOUNCEMENT_DETAIL)
    fun getSchoolAnnouncementDetail(
        @PathVariable schoolAnnouncementId: Long
    ): GetSchoolAnnouncementDetail.Response {
        return announcementService.getSchoolAnnouncementDetail(GetSchoolAnnouncementDetail.Request(schoolAnnouncementId))
    }


    @GetMapping(AnnouncementApi.V1.EDUCATION_OFFICE_ANNOUNCEMENT)
    fun getEducationOfficeAnnouncement(
        @RequestParam size: Int,
        @RequestParam page: Int
    ): GetEducationOfficeAnnouncementSlice.Response {
        return announcementService.getEducationOfficeAnnouncementSlice(
            GetEducationOfficeAnnouncementSlice.Request(
                size,
                page
            )
        )
    }


    @GetMapping(AnnouncementApi.V1.EDUCATION_OFFICE_ANNOUNCEMENT_DETAIL)
    fun getEducationOfficeAnnouncementDetail(
        @PathVariable educationOfficeAnnouncementId: Long
    ): GetEducationOfficeAnnouncementDetail.Response {
        return announcementService.getEducationOfficeAnnouncementDetail(
            GetEducationOfficeAnnouncementDetail.Request(
                educationOfficeAnnouncementId
            )
        )
    }


}