package com.asap.asapbackend.domain.timetable.domain.service

import com.asap.asapbackend.batch.timetable.TimetableInfoProvider
import com.asap.asapbackend.domain.classroom.domain.model.Grade
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomRepository
import com.asap.asapbackend.domain.timetable.domain.model.Subject
import com.asap.asapbackend.domain.timetable.domain.model.Timetable
import com.asap.asapbackend.domain.timetable.domain.repository.SubjectRepository
import com.asap.asapbackend.domain.timetable.domain.repository.TimetableRepository
import org.springframework.stereotype.Service

@Service
class TimetableAppender(
    private val classroomRepository: ClassroomRepository,
    private val subjectRepository: SubjectRepository,
    private val timetableRepository: TimetableRepository
) {

    fun addSubjectAndTimetable(timetables: List<TimetableInfoProvider.TimetableRequest>) {
        val existClassroom = classroomRepository.findBySchoolIn(timetables.map { it.school })

        val subjectList = mutableListOf<Subject>()
        val timetableList = mutableListOf<Timetable>()

        val existingSubject = subjectRepository.findByClassroomIn(existClassroom)
        subjectList.addAll(existingSubject)

        timetables.forEach {
            val classroom = existClassroom.find { room ->
                room.school.id == it.school.id && room.grade == Grade.convert(it.grade) && room.className == it.className
            }

            val existSubject = subjectList.find { subject ->
                subject.classroom.id == classroom?.id && subject.name == it.name && subject.semester == it.semester
            }

            if (existSubject == null && it.name != null && classroom !=null) {
                val subject = Subject(
                    classroom = classroom,
                    name = it.name,
                    semester = it.semester
                )
                subjectList.add(subject)

                val timetable = Timetable(
                    subject = subject,
                    day = it.day,
                    time = it.time
                )
                timetableList.add(timetable)
            } else if (it.name != null) {

                val timetable = Timetable(
                    subject = existSubject,
                    day = it.day,
                    time = it.time
                )
                timetableList.add(timetable)
            }
        }
        subjectRepository.saveAll(subjectList)
        timetableRepository.saveAll(timetableList)
    }
}