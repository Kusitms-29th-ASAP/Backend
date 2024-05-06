package com.asap.asapbackend.domain.teacher.domain.service

import com.asap.asapbackend.domain.teacher.domain.model.Teacher
import com.asap.asapbackend.domain.teacher.domain.repository.TeacherRepository
import org.springframework.stereotype.Service

@Service
class TeacherReader(
    private val teacherRepository: TeacherRepository
) {

    fun findByUsernameAndPassword(username: String, password: String) : Teacher {
        return findTeacher { teacherRepository.findByUsernameAndPassword(username, password) }
    }

    fun findByUsernameOrNull(username: String): Teacher? {
        return teacherRepository.findByUsername(username)
    }

    private fun findTeacher(function: () -> Teacher?): Teacher {
        return function() ?: throw IllegalArgumentException("Teacher not found")
    }
}