package com.asap.asapbackend.domain.menu.domain.service

import com.asap.asapbackend.domain.menu.domain.model.Menu
import com.asap.asapbackend.domain.menu.domain.repository.MenuRepository
import com.asap.asapbackend.domain.menu.event.MultiMenuCreateEvent
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class MenuAppender(
    private val menuRepository: MenuRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun addMenus(menus: List<Menu>) {
        val savedMenu = menuRepository.saveAll(menus)
        val event = MultiMenuCreateEvent(savedMenu.toSet())
        applicationEventPublisher.publishEvent(event)
    }
}