package com.asap.asapbackend.domain.announcement.event

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement

data class MultiEducationOfficeAnnouncementCreateEvent(
    val announcements: Set<EducationOfficeAnnouncement>
)


data class MultiSchoolAnnouncementCreateEvent(
    val announcements: Set<SchoolAnnouncement>
)