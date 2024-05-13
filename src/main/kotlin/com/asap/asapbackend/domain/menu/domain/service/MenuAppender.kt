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
    fun addMenu(menus: List<Menu>) {
        menuRepository.saveAll(menus)
    }
}