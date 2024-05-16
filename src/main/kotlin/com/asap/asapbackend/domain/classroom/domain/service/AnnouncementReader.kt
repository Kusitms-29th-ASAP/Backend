package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.domain.classroom.domain.model.Announcement
import com.asap.asapbackend.domain.classroom.domain.repository.AnnouncementRepository
import org.springframework.stereotype.Service

@Service
class AnnouncementReader (
    private val announcementRepository: AnnouncementRepository
){
    fun getRecentAnnouncementByTeacherIdOrNull(teacherId: Long) : Announcement?{
        return announcementRepository.findTopByTeacherIdOrderByCreatedAtDesc(teacherId)
    }

    fun getAllByTeacherId(teacherId: Long) : List<Announcement> {
        return announcementRepository.findAllByTeacherIdOrderByCreatedAtDesc(teacherId)
    }
}