package com.asap.asapbackend.infrastructure.jpa.timetable

import com.asap.asapbackend.domain.timetable.domain.model.Timetable
import com.asap.asapbackend.domain.timetable.domain.repository.TimetableRepository
import com.asap.asapbackend.infrastructure.jpa.timetable.repository.TimetableJpaRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class TimetableRepositoryImpl(
    private val timetableJpaRepository: TimetableJpaRepository,
    private val jdbcTemplate: JdbcTemplate
): TimetableRepository {
    override fun findByClassroomIdAndDayOrderByTime(classroomId: Long, day: LocalDate): List<Timetable> {
        return timetableJpaRepository.findByClassroomIdAndDayOrderByTime(classroomId, day)
            .map { TimetableMapper.toTimetableModel(it) }
    }

    override fun insertBatch(timetables: Collection<Timetable>) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO timetable (subject_id, day, time, created_at, updated_at) VALUES (?, ?, ?, now(),  now())",
            timetables.map {
                arrayOf(it.subject.id, it.day, it.time)
            }
        )
    }
}