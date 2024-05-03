package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.batch.classroom.model.ClassroomResponse
import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.model.Grade
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomRepository
import org.springframework.stereotype.Service
import com.asap.asapbackend.domain.classroom.domain.model.Year as year

@Service
class ClassroomAppender(
        private val classroomRepository: ClassroomRepository
) {
    fun addClassroom(classrooms: List<ClassroomResponse>) {
        for (classroom in classrooms) {
            if(classroomRepository.findBySchoolAndGradeAndClassNumber(classroom.school, Grade.convert(classroom.grade),classroom.classNumber)==null){
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
}