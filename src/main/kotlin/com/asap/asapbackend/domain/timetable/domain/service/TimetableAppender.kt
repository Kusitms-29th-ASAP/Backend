package com.asap.asapbackend.domain.timetable.domain.service

import com.asap.asapbackend.batch.timetable.TimetableInfoProvider
import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomRepository
import com.asap.asapbackend.domain.timetable.domain.model.Subject
import com.asap.asapbackend.domain.timetable.domain.model.Timetable
import com.asap.asapbackend.domain.timetable.domain.repository.SubjectRepository
import com.asap.asapbackend.domain.timetable.domain.repository.TimetableRepository
import com.asap.asapbackend.domain.timetable.event.MultiSubjectCreateEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service


@Service
class TimetableAppender(
    private val classroomRepository: ClassroomRepository,
    private val subjectRepository: SubjectRepository,
    private val timetableRepository: TimetableRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun addSubjectAndTimetable(timetables: List<TimetableInfoProvider.TimetableResponse>) {
        val existClassroomMap = classroomRepository.findBySchoolIn(timetables.map { it.school })
            .groupBy { it.school }

        val subjectList = subjectRepository.findOriginalSubjectsByClassroomIn(existClassroomMap.values.flatten())
            .groupByTo(mutableMapOf()) { it.classroomId }

        timetables.forEach {
            if (it.name == null) return@forEach

            val classroom = existClassroomMap[it.school]?.find { subject ->
                subject.isSameClassroom(Classroom(it.grade, it.className, it.school))
            } ?: return@forEach

            subjectList[classroom.id] ?: Subject(
                classroomId = classroom.id,
                name = it.name,
                semester = it.semester
            ).also {
                subjectList.getOrPut(classroom.id) { mutableListOf() }.add(it)
            }

        }

        val savedSubjects = subjectRepository.saveAll(subjectList.values.flatten())

        val savedTimetables = mutableSetOf<Timetable>()

        timetables.forEach {
            if (it.name == null) return@forEach

            val classroom = existClassroomMap[it.school]?.find { classroom ->
                classroom.isSameClassroom(Classroom(it.grade, it.className, it.school))
            } ?: return@forEach

            val existSubject = savedSubjects.find { subject ->
                subject.isSameSubject(it.name, it.semester, classroom.id)
            } ?: return@forEach

            savedTimetables.add(
                Timetable(
                    subject = existSubject,
                    day = it.day,
                    time = it.time
                )
            )
        }

        timetableRepository.insertBatch(savedTimetables)

        applicationEventPublisher.publishEvent(MultiSubjectCreateEvent(subjectList.values.flatten().toSet()))
    }
}