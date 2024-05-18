package com.asap.asapbackend.batch.announcement

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncementPage

interface SchoolAnnouncementInfoProvider {
    fun retrieveAnnouncementInfo(batchSize: Int, pageNumber: Int): SchoolAnnouncementDataContainer


    data class SchoolAnnouncementDataContainer(
        val schoolAnnouncementInfo: List<SchoolAnnouncementInfo>,
        val hasNext: Boolean
    )

    data class SchoolAnnouncementInfo(
        val schoolAnnouncementPage: SchoolAnnouncementPage,
        val index: Int,
        val title: String,
        val imageUrls: List<String>
    )
}