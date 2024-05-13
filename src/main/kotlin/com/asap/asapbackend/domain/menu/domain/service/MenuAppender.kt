package com.asap.asapbackend.domain.menu.domain.service

import com.asap.asapbackend.batch.menu.MenuInfoProvider
import com.asap.asapbackend.domain.menu.domain.model.Allergy
import com.asap.asapbackend.domain.menu.domain.model.Food
import com.asap.asapbackend.domain.menu.domain.model.Menu
import com.asap.asapbackend.domain.menu.domain.repository.MenuRepository
import org.springframework.stereotype.Service

@Service
class MenuAppender(
    private val menuRepository: MenuRepository
) {
    fun addMenu(menus: List<MenuInfoProvider.MenuResponse>) {
        val menuList = mutableListOf<Menu>()
        menus.forEach {
            val menu = Menu(
                it.school, it.day, it.menu
            )
            menuList.add(menu)
        }
        menuRepository.saveAll(menuList)
    }
}