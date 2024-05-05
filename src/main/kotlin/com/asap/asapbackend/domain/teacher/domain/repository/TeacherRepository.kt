package com.asap.asapbackend.domain.teacher.domain.repository

import com.asap.asapbackend.domain.teacher.domain.model.Teacher
import org.springframework.data.jpa.repository.JpaRepository

interface TeacherRepository : JpaRepository<Teacher, Long> {

    fun findByUsernameAndPassword(username: String, password: String): Teacher?

    fun findByUsername(username: String): Teacher?
}
