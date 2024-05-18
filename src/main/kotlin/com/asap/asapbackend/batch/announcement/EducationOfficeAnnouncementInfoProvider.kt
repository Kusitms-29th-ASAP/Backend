package com.asap.asapbackend.batch.announcement

interface EducationOfficeAnnouncementInfoProvider {

    fun retrieveAnnouncementInfo(batchSize: Int, startIndex: Int): EducationOfficeAnnouncementDataContainer

    data class EducationOfficeAnnouncementDataContainer(
        val educationOfficeAnnouncementInfo: List<EducationOfficeAnnouncementInfo>,
        val hasNext: Boolean
    )

    data class EducationOfficeAnnouncementInfo(
        val index: Int,
        val title: String,
        val imageUrls: List<String>
    )
}