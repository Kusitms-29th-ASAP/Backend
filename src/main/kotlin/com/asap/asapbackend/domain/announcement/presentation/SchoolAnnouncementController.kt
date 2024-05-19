package com.asap.asapbackend.domain.announcement.presentation

import com.asap.asapbackend.domain.announcement.application.SchoolAnnouncementService
import com.asap.asapbackend.domain.announcement.application.dto.*
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.*

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


    @PatchMapping(AnnouncementApi.V1.SCHOOL_ANNOUNCEMENT_CATEGORY)
    fun patchSchoolAnnouncementCategory(
        @PathVariable schoolAnnouncementId: Long,
        @RequestBody updateRequest: UpdateSchoolAnnouncementCategory.Request
    ) {
        announcementService.updateSchoolAnnouncementCategory(updateRequest, schoolAnnouncementId)
    }

    @PatchMapping(AnnouncementApi.V1.EDUCATION_OFFICE_ANNOUNCEMENT_CATEGORY)
    fun patchEducationOfficeAnnouncementCategory(
        @PathVariable educationOfficeAnnouncementId: Long,
        @RequestBody updateRequest: UpdateEducationOfficeAnnouncementCategory.Request
    ) {
        announcementService.updateEducationOfficeAnnouncementCategory(updateRequest, educationOfficeAnnouncementId)
    }
}