package com.asap.asapbackend.infrastructure.jpa.announcement

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import com.asap.asapbackend.infrastructure.jpa.announcement.entity.EducationOfficeAnnouncementEntity
import com.asap.asapbackend.infrastructure.jpa.announcement.entity.SchoolAnnouncementEntity

object AnnouncementMapper {

    fun toEntity(announcement: EducationOfficeAnnouncement): EducationOfficeAnnouncementEntity {
        return EducationOfficeAnnouncementEntity(
            id = announcement.id,
            idx = announcement.idx,
            title = announcement.title,
            imageUrls = announcement.imageUrls,
            summaries = announcement.summaries,
            keywords = announcement.keywords,
            createdAt = announcement.createdAt,
            updatedAt = announcement.updatedAt
        )
    }

    fun toModel(entity: EducationOfficeAnnouncementEntity): EducationOfficeAnnouncement {
        return EducationOfficeAnnouncement(
            id= entity.id,
            idx = entity.idx,
            title = entity.title,
            imageUrls = entity.imageUrls,
            summaries = entity.summaries,
            keywords = entity.keywords,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(announcement: SchoolAnnouncement): SchoolAnnouncementEntity {
        return SchoolAnnouncementEntity(
            id = announcement.id,
            schoolAnnouncementPageId = announcement.schoolAnnouncementPageId,
            index = announcement.idx,
            title = announcement.title,
            imageUrls = announcement.imageUrls,
            summaries = announcement.summaries,
            keywords = announcement.keywords,
            createdAt = announcement.createdAt,
            updatedAt = announcement.updatedAt
        )
    }

    fun toModel(entity: SchoolAnnouncementEntity): SchoolAnnouncement {
        return SchoolAnnouncement(
            id= entity.id,
            schoolAnnouncementPageId = entity.schoolAnnouncementPageId,
            index = entity.idx,
            title = entity.title,
            imageUrls = entity.imageUrls,
            summaries = entity.summaries,
            keywords = entity.keywords,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}