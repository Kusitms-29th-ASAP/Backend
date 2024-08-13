package com.asap.asapbackend.domain.menu.domain.repository

import com.asap.asapbackend.domain.menu.domain.model.Menu
import java.time.LocalDate

interface MenuRepository{
    fun findBySchoolIdAndDay(schoolId: Long, day: LocalDate): Menu?

    fun findBySchoolIdAndDayInOrderByDayAsc(schoolId: Long, day: List<LocalDate>): List<Menu>

    fun saveAll(menus: List<Menu>): List<Menu>
}