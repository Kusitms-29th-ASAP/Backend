package com.asap.asapbackend.domain.menu.domain.service

import com.asap.asapbackend.domain.menu.domain.model.Menu
import com.asap.asapbackend.domain.menu.domain.repository.MenuRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class MenuReader (
    private val menuRepository: MenuRepository
){
    fun findTodayMenuBySchoolId(schoolId: Long) : Menu? {
        return menuRepository.findBySchoolIdAndDay(schoolId,LocalDate.now())
    }

    fun findThisMonthMenuBySchoolId(schoolId: Long) : List<Menu?> {
        val firstDayOfMonth = LocalDate.now().withDayOfMonth(1)
        val lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())

        val daysOfMonth = firstDayOfMonth.datesUntil(lastDayOfMonth.plusDays(1)).toList()
        println(daysOfMonth)
        return menuRepository.findBySchoolIdAndDayIn(schoolId,daysOfMonth)
    }
}