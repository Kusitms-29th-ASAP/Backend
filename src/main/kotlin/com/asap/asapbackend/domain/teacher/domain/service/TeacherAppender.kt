package com.asap.asapbackend.domain.teacher.domain.service

import com.asap.asapbackend.domain.teacher.domain.model.Teacher
import com.asap.asapbackend.domain.teacher.domain.repository.TeacherRepository
import org.springframework.stereotype.Service

@Service
class TeacherAppender(
    private val teacherRepository: TeacherRepository
) {

    fun appendTeacher(teacher: Teacher){
        teacherRepository.save(teacher)
    }
}