package com.asap.asapbackend.domain.classroom.event

import com.asap.asapbackend.domain.classroom.domain.model.ClassroomAnnouncement

data class ClassroomAnnouncementCreateEvent(
    val classroomAnnouncement: ClassroomAnnouncement
)
