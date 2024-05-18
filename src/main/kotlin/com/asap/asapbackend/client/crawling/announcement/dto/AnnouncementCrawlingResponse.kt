package com.asap.asapbackend.client.crawling.announcement.dto

import com.asap.asapbackend.batch.announcement.EducationOfficeAnnouncementInfoProvider
import com.asap.asapbackend.batch.announcement.SchoolAnnouncementInfoProvider
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncementPage

data class AnnouncementCrawlingResponse(
    val data: List<AnnouncementDetail>
){
    fun convertToSchoolAnnouncement(schoolAnnouncementPage: SchoolAnnouncementPage): List<SchoolAnnouncementInfoProvider.SchoolAnnouncementInfo> {
        return data.map { announcementDetail ->
            SchoolAnnouncementInfoProvider.SchoolAnnouncementInfo(
                schoolAnnouncementPage = schoolAnnouncementPage,
                index = announcementDetail.idx.toInt(),
                title = announcementDetail.file_info.first().title,
                imageUrls = announcementDetail.file_info.flatMap { it.image_url }.sorted(),
            )
        }
    }

    fun convertToEducationOfficeAnnouncement(): List<EducationOfficeAnnouncementInfoProvider.EducationOfficeAnnouncementInfo> {
        return data.map { announcementDetail ->
            EducationOfficeAnnouncementInfoProvider.EducationOfficeAnnouncementInfo(
                index = announcementDetail.idx.toInt(),
                title = announcementDetail.file_info.first().title,
                imageUrls = announcementDetail.file_info.flatMap { it.image_url }.sorted()
            )
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