package com.asap.asapbackend.domain.teacher.domain.service

import com.asap.asapbackend.domain.teacher.domain.exception.TeacherException
import com.asap.asapbackend.domain.teacher.domain.model.Teacher
import com.asap.asapbackend.domain.teacher.domain.repository.TeacherRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TeacherReader(
    private val teacherRepository: TeacherRepository
) {

    fun findByUsernameAndPassword(username: String, password: String, matcher: (String, String) -> Boolean) : Teacher {
        val teacher = findByUsername(username)
        if (matcher(password, teacher.password)) {
            return teacher
        }
        throw TeacherException.TeacherNotFoundException()
    }

    fun findByUsername(username: String) : Teacher {
        return findTeacher { teacherRepository.findByUsername(username) }
    }

    fun findById(id: Long): Teacher {
        return findTeacher { teacherRepository.findByIdOrNull(id) }
    }

    private fun findTeacher(function: () -> Teacher?): Teacher {
        return function() ?: throw TeacherException.TeacherNotFoundException()
    }
}