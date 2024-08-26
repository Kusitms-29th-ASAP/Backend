package com.asap.asapbackend.domain.timetable.domain.repository

import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.timetable.domain.model.Subject

interface SubjectRepository{
    fun findByClassroomIn(classroom: List<Classroom>) : List<Subject>
    fun saveAll(subjects: List<Subject>): List<Subject>
    fun findOriginalSubjectsByClassroomIn(classroom: List<Classroom>) : List<Subject>

    fun findAllByClassroomId(classroomId: Long): List<Subject>
}