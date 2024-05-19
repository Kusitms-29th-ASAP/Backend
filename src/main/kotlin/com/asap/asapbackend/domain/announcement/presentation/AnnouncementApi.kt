package com.asap.asapbackend.domain.announcement.presentation

object AnnouncementApi {

    object V1{
        const val BASE_URL = "/api/v1/announcements"


        const val SCHOOL_ANNOUNCEMENT = "$BASE_URL/schools"
        const val SCHOOL_ANNOUNCEMENT_DETAIL = "$SCHOOL_ANNOUNCEMENT/{schoolAnnouncementId}"
        const val EDUCATION_OFFICE_ANNOUNCEMENT = "$BASE_URL/education-offices"
        const val EDUCATION_OFFICE_ANNOUNCEMENT_DETAIL = "$EDUCATION_OFFICE_ANNOUNCEMENT/{educationOfficeAnnouncementId}"
    }
}