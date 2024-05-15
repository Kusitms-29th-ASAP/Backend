package com.asap.asapbackend.domain.menu.presentation

import com.asap.asapbackend.domain.menu.application.MenuService
import com.asap.asapbackend.domain.menu.application.dto.GetThisMonthMenu
import com.asap.asapbackend.domain.menu.application.dto.GetTodayMenu
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MenuController(
    private val menuService: MenuService
){
    @GetMapping(MenuApi.V1.TODAY)
    fun getTodayMenu() : GetTodayMenu.Response {
        return menuService.getTodayMenu()
    }

    @GetMapping(MenuApi.V1.MONTH)
    fun getThisMonthMenu() : GetThisMonthMenu.Response {
        return menuService.getThisMonthMenu()
    }

}