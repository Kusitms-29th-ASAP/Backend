package com.asap.asapbackend.batch.classroom.service

import com.asap.asapbackend.batch.classroom.model.ClassroomResponse
import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomRepository
import org.springframework.stereotype.Service
import com.asap.asapbackend.domain.classroom.domain.model.Year as year

@Service
class ClassroomAppender(
        private val classroomRepository: ClassroomRepository
) {
    fun addClassroom(classrooms: List<ClassroomResponse>) {
        classroomRepository.deleteAll() // 중복 방지용. 어떤 방법이 더 좋을지 고민해봐야 할 듯
        for (classroom in classrooms) {
            val newClassroom = Classroom(
                    grade = classroom.grade,
                    classNumber = classroom.classNumber,
                    year = year(),
                    school = classroom.school
            )
            classroomRepository.save(newClassroom)
        }
    }
}