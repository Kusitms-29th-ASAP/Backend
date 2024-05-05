package com.asap.asapbackend.domain.teacher.domain.service

import com.asap.asapbackend.domain.teacher.domain.exception.TeacherErrorCode
import com.asap.asapbackend.domain.teacher.domain.exception.TeacherException
import com.asap.asapbackend.domain.teacher.domain.repository.TeacherRepository
import org.springframework.stereotype.Service

@Service
class TeacherValidator(
    private val teacherRepository: TeacherRepository
) {
    fun validateTeacherCreatable(username: String) {
        teacherRepository.findByUsername(username)?.let {
            throw TeacherException(TeacherErrorCode.TEACHER_NOT_FOUND)
        }
    }
}