package com.asap.asapbackend.batch.announcement

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncementPage

interface AnnouncementInfoProvider {
    fun retrieveAnnouncementInfo(batchSize: Int, pageNumber: Int): AnnouncementDataContainer


    data class AnnouncementDataContainer(
        val announcementInfo: List<AnnouncementInfo>,
        val hasNext: Boolean
    )

    data class AnnouncementInfo(
        val schoolAnnouncementPage: SchoolAnnouncementPage,
        val index: Int,
        val title: String,
        val imageUrls: List<String>
    )
}