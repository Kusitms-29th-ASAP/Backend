package com.asap.asapbackend.domain.timetable.domain.service

import com.asap.asapbackend.batch.timetable.TimetableInfoProvider
import com.asap.asapbackend.domain.timetable.domain.model.Subject
import com.asap.asapbackend.domain.timetable.domain.model.Timetable
import com.asap.asapbackend.domain.timetable.domain.repository.SubjectRepository
import com.asap.asapbackend.domain.timetable.domain.repository.TimetableRepository
import org.springframework.stereotype.Service

@Service
class TimetableAppender(
    private val subjectRepository: SubjectRepository,
    private val timetableRepository: TimetableRepository
) {

    fun addSubjectAndTimetable(timetables: List<TimetableInfoProvider.TimetableRequest>) {
        timetables.forEach {
            val existSubject = subjectRepository.findByClassroomAndNameAndSemester(
                it.classroom,
                it.name,
                it.semester
            )
            if (existSubject == null && it.name != null) {
                val subject = Subject(
                    it.classroom,
                    it.name,
                    it.semester
                )
                subjectRepository.save(subject)
            }
        }
        timetables.forEach {
            if (it.name != null) {
                val subject = subjectRepository.findByClassroomAndNameAndSemester(
                    it.classroom,
                    it.name,
                    it.semester
                )
                val timetable = Timetable(
                    subject,
                    it.day,
                    it.time
                )
                timetableRepository.save(timetable)
            }
        }
    }
}