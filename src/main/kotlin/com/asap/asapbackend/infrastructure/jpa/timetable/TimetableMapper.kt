package com.asap.asapbackend.infrastructure.jpa.timetable

import com.asap.asapbackend.domain.timetable.domain.model.Subject
import com.asap.asapbackend.domain.timetable.domain.model.Timetable
import com.asap.asapbackend.infrastructure.jpa.timetable.entity.SubjectEntity
import com.asap.asapbackend.infrastructure.jpa.timetable.entity.TimetableEntity

object TimetableMapper {

    fun toTimetableModel(timetableEntity: TimetableEntity): Timetable {
        return Timetable(
            id = timetableEntity.id,
            day = timetableEntity.day,
            time = timetableEntity.time,
            subject = toSubjectModel(timetableEntity.subject),
            createdAt = timetableEntity.createdAt,
            updatedAt = timetableEntity.updatedAt
        )
    }

    fun toSubjectModel(subjectEntity: SubjectEntity): Subject {
        return Subject(
            id = subjectEntity.id,
            classroomId = subjectEntity.classroomId,
            name = subjectEntity.name,
            semester = subjectEntity.semester,
            createdAt = subjectEntity.createdAt,
            updatedAt = subjectEntity.updatedAt
        )
    }

    fun toTimetableEntity(timetable: Timetable): TimetableEntity {
        return TimetableEntity(
            id = timetable.id,
            day = timetable.day,
            time = timetable.time,
            subjectId = timetable.subject.id,
            createdAt = timetable.createdAt,
            updatedAt = timetable.updatedAt
        )
    }

    fun toSubjectEntity(subject: Subject): SubjectEntity {
        return SubjectEntity(
            id = subject.id,
            classroomId = subject.classroomId,
            name = subject.name,
            semester = subject.semester,
            createdAt = subject.createdAt,
            updatedAt = subject.updatedAt
        )
    }
}