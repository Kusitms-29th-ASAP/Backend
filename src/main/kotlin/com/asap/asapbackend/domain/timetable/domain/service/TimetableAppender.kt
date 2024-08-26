package com.asap.asapbackend.domain.timetable.domain.service

import com.asap.asapbackend.batch.timetable.TimetableInfoProvider
import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomRepository
import com.asap.asapbackend.domain.school.domain.model.School
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

        val savedTimetables = mutableSetOf<Timetable>()

        handleTimetables(timetables, existClassroomMap, subjectList) { subject, timetableResponse ->
            savedTimetables.add(
                Timetable(
                    subject = subject,
                    day = timetableResponse.day,
                    time = timetableResponse.time
                )
            )
        }

        timetableRepository.insertBatch(savedTimetables)

        applicationEventPublisher.publishEvent(MultiSubjectCreateEvent(subjectList.values.flatten().toSet()))
    }

    private fun handleTimetables(
        timetables: List<TimetableInfoProvider.TimetableResponse>,
        existClassroomMap: Map<School, List<Classroom>>,
        subjectList: MutableMap<Long, MutableList<Subject>>,
        subjectHandler: (Subject, TimetableInfoProvider.TimetableResponse) -> Unit = { _, _ -> }
    ) {
        timetables.forEach {
            if (it.name == null) return@forEach

            val classroom = existClassroomMap[it.school]?.find { subject ->
                subject.isSameClassroom(Classroom(it.grade, it.className, it.school))
            } ?: return@forEach

            val subject = subjectList[classroom.id]?.let { findSubjectList ->
                findSubjectList.find { subject ->
                    subject.isSameSubject(it.name, it.semester, classroom.id)
                }
            } ?: run {
                val savedSubject = subjectRepository.save(
                    Subject(
                        name = it.name,
                        semester = it.semester,
                        classroomId = classroom.id
                    )
                )
                subjectList.getOrPut(classroom.id) { mutableListOf() }.add(savedSubject)
                savedSubject
            }

            subjectHandler(subject, it)
        }
    }
}