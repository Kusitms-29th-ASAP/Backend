package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.batch.classroom.dto.ClassroomResponse
import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.domain.classroom.domain.model.ChildClassroom
import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.model.Grade
import com.asap.asapbackend.domain.classroom.domain.model.TeacherClassroom
import com.asap.asapbackend.domain.classroom.domain.repository.ChildClassRoomRepository
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomRepository
import com.asap.asapbackend.domain.classroom.domain.repository.TeacherClassroomRepository
import com.asap.asapbackend.domain.teacher.domain.model.Teacher
import org.springframework.stereotype.Service

@Service
class ClassroomAppender(
    private val classroomRepository: ClassroomRepository,
    private val childClassRoomRepository: ChildClassRoomRepository,
    private val teacherClassroomRepository: TeacherClassroomRepository
) {

    fun addChild(classroom: Classroom, student: Child) {
        childClassRoomRepository.save(
            ChildClassroom(
                classroom = classroom,
                student = student
            )
        )
    }

    fun addTeacher(classroom: Classroom, teacher: Teacher) {
        teacherClassroomRepository.save(
            TeacherClassroom(
                classroom = classroom,
                teacher = teacher
            )
        )
    }

    fun addClassroom(classrooms: List<ClassroomResponse>) { // TODO : 리팩터링
        for (classroom in classrooms) {
            if(classroomRepository.findBySchoolAndGradeAndClassName(classroom.school, Grade.convert(classroom.grade),classroom.classNumber)==null){
                val newClassroom = Classroom(
                        grade = classroom.grade,
                        className = classroom.classNumber,
                        school = classroom.school
                )
                classroomRepository.save(newClassroom)
            }
        }
    }
}