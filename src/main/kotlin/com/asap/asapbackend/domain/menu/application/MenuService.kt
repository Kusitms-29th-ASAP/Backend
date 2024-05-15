package com.asap.asapbackend.domain.menu.application

import com.asap.asapbackend.domain.child.domain.service.ChildReader
import com.asap.asapbackend.domain.classroom.domain.service.ClassroomReader
import com.asap.asapbackend.domain.menu.application.dto.GetThisMonthMenu
import com.asap.asapbackend.domain.menu.application.dto.GetTodayMenu
import com.asap.asapbackend.domain.menu.domain.service.MenuReader
import com.asap.asapbackend.global.security.getCurrentUserId
import org.springframework.stereotype.Service

@Service
class MenuService(
    private val childReader: ChildReader,
    private val classroomReader: ClassroomReader,
    private val menuReader: MenuReader
) {
    fun getTodayMenu(): GetTodayMenu.Response {
        val userId = getCurrentUserId()
        val childId = childReader.findPrimaryChild(userId)!!.id
        val schoolId = classroomReader.findByStudent(childId).school.id
        val foods = menuReader.findTodayMenuBySchoolId(schoolId)?.foods
        val childAllergies = childReader.findPrimaryChild(userId)!!.allergies.toList()
        val todayMenu = GetTodayMenu().toFood(foods, childAllergies)
        return GetTodayMenu.Response(todayMenu)
    }

    fun getThisMonthMenu(): GetThisMonthMenu.Response {
        val userId = getCurrentUserId()
        val childId = childReader.findPrimaryChild(userId)!!.id
        val schoolId = classroomReader.findByStudent(childId).school.id
        val menus = menuReader.findThisMonthMenuBySchoolId(schoolId)
        val childAllergies = childReader.findPrimaryChild(userId)!!.allergies.toList()
        val monthMenuList = menus.map {
            GetThisMonthMenu().toFood(menus, childAllergies)
        }
        return GetThisMonthMenu.Response(monthMenuList)
    }
}