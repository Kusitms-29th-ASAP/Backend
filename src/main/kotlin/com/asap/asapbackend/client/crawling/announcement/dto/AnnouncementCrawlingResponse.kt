package com.asap.asapbackend.client.crawling.announcement.dto

import com.asap.asapbackend.batch.announcement.EducationOfficeAnnouncementInfoProvider
import com.asap.asapbackend.batch.announcement.SchoolAnnouncementInfoProvider
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncementPage

data class AnnouncementCrawlingResponse(
    val data: List<AnnouncementDetail>
){
    fun convertToAnnouncement(schoolAnnouncementPage: SchoolAnnouncementPage): List<SchoolAnnouncementInfoProvider.SchoolAnnouncementInfo> {
        return data.flatMap {
            it.file_info.map { fileInfo ->
                SchoolAnnouncementInfoProvider.SchoolAnnouncementInfo(
                    schoolAnnouncementPage = schoolAnnouncementPage,
                    index = it.idx.toInt(),
                    title = fileInfo.title,
                    imageUrls = fileInfo.image_url
                )
            }
        }
    }

    fun convertToAnnouncement(): List<EducationOfficeAnnouncementInfoProvider.EducationOfficeAnnouncementInfo> {
        return data.flatMap {
            it.file_info.map { fileInfo ->
                EducationOfficeAnnouncementInfoProvider.EducationOfficeAnnouncementInfo(
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