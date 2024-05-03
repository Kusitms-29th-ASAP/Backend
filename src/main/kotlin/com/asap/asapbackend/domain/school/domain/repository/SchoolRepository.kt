package com.asap.asapbackend.domain.school.domain.repository

import com.asap.asapbackend.domain.school.domain.model.School
import org.springframework.data.jpa.repository.JpaRepository

interface SchoolRepository: JpaRepository<School, Long> {
}