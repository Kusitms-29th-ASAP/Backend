package com.asap.asapbackend.infrastructure.jpa.classroom

import com.asap.asapbackend.domain.classroom.domain.model.ClassroomAnnouncement
import com.asap.asapbackend.infrastructure.jpa.classroom.entity.ClassroomAnnouncementEntity

object ClassroomMapper {

    fun toClassroomAnnouncementEntity(classroomAnnouncement: ClassroomAnnouncement): ClassroomAnnouncementEntity {
        return ClassroomAnnouncementEntity(
            id = classroomAnnouncement.id,
            descriptions = classroomAnnouncement.descriptions,
            classroomId = classroomAnnouncement.classroomId,
            teacherId = classroomAnnouncement.teacherId,
            createdAt = classroomAnnouncement.createdAt,
            updatedAt = classroomAnnouncement.updatedAt
        )
    }

    fun toClassroomAnnouncementModel(classroomAnnouncementEntity: ClassroomAnnouncementEntity): ClassroomAnnouncement {
        return ClassroomAnnouncement(
            id = classroomAnnouncementEntity.id,
            descriptions = classroomAnnouncementEntity.descriptions,
            classroomId = classroomAnnouncementEntity.classroomId,
            teacherId = classroomAnnouncementEntity.teacherId,
            createdAt = classroomAnnouncementEntity.createdAt,
            updatedAt = classroomAnnouncementEntity.updatedAt
        )
    }
}