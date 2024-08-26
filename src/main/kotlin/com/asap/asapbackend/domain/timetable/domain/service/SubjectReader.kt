package com.asap.asapbackend.domain.timetable.domain.service

import com.asap.asapbackend.domain.timetable.domain.model.Subject
import com.asap.asapbackend.domain.timetable.domain.repository.SubjectRepository
import org.springframework.stereotype.Service

@Service
class SubjectReader(
    private val subjectRepository: SubjectRepository
) {

    fun findSubjectMapByClassroomId(classroomId: Long): Map<Long, Subject> {
        return subjectRepository.findAllByClassroomId(classroomId).associateBy { it.id }
    }
}