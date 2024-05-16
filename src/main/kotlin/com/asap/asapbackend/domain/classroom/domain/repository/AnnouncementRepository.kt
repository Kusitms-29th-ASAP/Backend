package com.asap.asapbackend.domain.classroom.domain.repository

import com.asap.asapbackend.domain.classroom.domain.model.Announcement
import org.springframework.data.jpa.repository.JpaRepository

interface AnnouncementRepository: JpaRepository<Announcement,Long> {
    fun findTopByTeacherIdOrderByCreatedAtDesc(teacherId : Long) : Announcement?

    fun findAllByTeacherIdOrderByCreatedAtDesc(teacherId: Long) : List<Announcement>
}