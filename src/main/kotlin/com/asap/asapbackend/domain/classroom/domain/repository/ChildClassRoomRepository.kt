package com.asap.asapbackend.domain.classroom.domain.repository

import com.asap.asapbackend.domain.classroom.domain.model.ChildClassroom
import org.springframework.data.jpa.repository.JpaRepository

interface ChildClassRoomRepository: JpaRepository<ChildClassroom, Long> {
}