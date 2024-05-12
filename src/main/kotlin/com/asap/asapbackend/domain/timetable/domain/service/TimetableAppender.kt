package com.asap.asapbackend.domain.timetable.domain.service

import com.asap.asapbackend.batch.timetable.TimetableInfoProvider
import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomRepository
import com.asap.asapbackend.domain.timetable.domain.model.Subject
import com.asap.asapbackend.domain.timetable.domain.model.Timetable
import com.asap.asapbackend.domain.timetable.domain.repository.SubjectRepository
import com.asap.asapbackend.domain.timetable.domain.repository.TimetableJdbcRepository
import com.asap.asapbackend.domain.timetable.domain.repository.TimetableRepository
import org.springframework.stereotype.Service


@Service
class TimetableAppender(
    private val classroomRepository: ClassroomRepository,
    private val subjectRepository: SubjectRepository,
    private val timetableRepository: TimetableRepository,
    private val timetableJdbcRepository: TimetableJdbcRepository
) {

    fun addSubjectAndTimetable(timetables: List<TimetableInfoProvider.TimetableResponse>) {
        val existClassroomMap = classroomRepository.findBySchoolIn(timetables.map { it.school })
            .groupBy { it.school }
        val timetableList = mutableSetOf<Timetable>()
        val subjectList = subjectRepository.findByClassroomIn(existClassroomMap.values.flatten())
            .groupByTo(mutableMapOf()) { it.classroom }

        timetables.forEach {
            if (it.name == null) return@forEach

            val classroom = existClassroomMap[it.school]?.find { classroom ->
                classroom.isSameClassroom(Classroom(it.grade, it.className, it.school))
            } ?: return@forEach


            val existSubject = subjectList[classroom]?.find { subject ->
                subject.isSameSubject(Subject(classroom, it.name, it.semester))
            } ?: Subject(classroom, it.name, it.semester).also {
                subjectList.getOrPut(classroom) { mutableListOf() }.add(it)
            }

            val timetable = Timetable(
                subject = existSubject,
                day = it.day,
                time = it.time
            )
            timetableList.add(timetable)
        }

        subjectRepository.saveAll(subjectList.values.flatten())
        timetableJdbcRepository.insertBatch(timetableList)
    }
}