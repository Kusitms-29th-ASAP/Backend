package com.asap.asapbackend.domain.menu.domain.repository

import com.asap.asapbackend.domain.menu.domain.model.Menu
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface MenuRepository : JpaRepository<Menu, Long> {
    fun findBySchoolIdAndDay(schoolId: Long, day: LocalDate): Menu?

    fun findBySchoolIdAndDayIn(schoolId: Long, day: List<LocalDate>): List<Menu>
}