package com.asap.asapbackend.domain.menu.domain.service

import com.asap.asapbackend.domain.menu.domain.model.Menu
import com.asap.asapbackend.domain.menu.domain.repository.MenuRepository
import org.springframework.cglib.core.Local
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate

@Service
class MenuReader(
    private val menuRepository: MenuRepository
) {
    fun findTodayMenuBySchoolId(schoolId: Long): Menu? {
        return menuRepository.findBySchoolIdAndDay(schoolId, LocalDate.now())
    }

    fun findThisMonthMenuBySchoolId(schoolId: Long): Map<LocalDate, Menu?> {
        val firstDayOfMonth = LocalDate.now().withDayOfMonth(1)
        val lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
        val daysOfMonth = firstDayOfMonth.datesUntil(lastDayOfMonth.plusDays(1)).toList()

        return daysOfMonth.associateWith { day ->
            menuRepository.findBySchoolIdAndDay(schoolId, day)
        }
    }
}