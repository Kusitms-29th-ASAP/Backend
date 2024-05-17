package com.asap.asapbackend.client.crawling.announcement.dto

import com.asap.asapbackend.batch.announcement.AnnouncementInfoProvider
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncementPage

data class AnnouncementCrawlingResponse(
    val data: List<AnnouncementDetail>
){
    fun convertToAnnouncement(schoolAnnouncementPage: SchoolAnnouncementPage): List<AnnouncementInfoProvider.AnnouncementInfo> {
        return data.flatMap {
            it.file_info.map { fileInfo ->
                AnnouncementInfoProvider.AnnouncementInfo(
                    schoolAnnouncementPage = schoolAnnouncementPage,
                    index = it.idx.toInt(),
                    title = fileInfo.title,
                    imageUrls = fileInfo.image_url
                )
            }
        }
    }
}

data class AnnouncementDetail(
    val idx: String,
    val file_info: List<AnnouncementFileInfo>
)

data class AnnouncementFileInfo(
    val title: String,
    val image_url: List<String>
)