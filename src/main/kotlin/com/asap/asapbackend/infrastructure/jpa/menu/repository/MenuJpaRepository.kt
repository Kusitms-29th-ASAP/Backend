package com.asap.asapbackend.infrastructure.jpa.menu.repository

import com.asap.asapbackend.infrastructure.jpa.menu.entity.MenuEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface MenuJpaRepository : JpaRepository<MenuEntity, Long> {

    fun findBySchoolIdAndDay(schoolId: Long, day: LocalDate): MenuEntity?

    fun findBySchoolIdAndDayInOrderByDayAsc(schoolId: Long, day: List<LocalDate>): List<MenuEntity>
}