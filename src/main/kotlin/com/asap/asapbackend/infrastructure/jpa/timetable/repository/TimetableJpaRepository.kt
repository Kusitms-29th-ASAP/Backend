package com.asap.asapbackend.infrastructure.jpa.timetable.repository

import com.asap.asapbackend.infrastructure.jpa.timetable.entity.TimetableEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface TimetableJpaRepository : JpaRepository<TimetableEntity, Long> {
    @Query(
        """
        SELECT t
        FROM TimetableEntity t
        WHERE t.subject.classroomId = :classroomId
        AND t.day = :day
        ORDER BY t.time
    """
    )
    fun findByClassroomIdAndDayOrderByTime(classroomId: Long, day: LocalDate): List<TimetableEntity>
}