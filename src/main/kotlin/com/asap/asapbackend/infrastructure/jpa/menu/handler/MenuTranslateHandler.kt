package com.asap.asapbackend.infrastructure.jpa.menu.handler

import com.asap.asapbackend.domain.menu.event.MultiMenuCreateEvent
import com.asap.asapbackend.global.util.TextTranslator
import com.asap.asapbackend.infrastructure.jpa.menu.entity.FoodEntity
import com.asap.asapbackend.infrastructure.jpa.menu.entity.TranslatedMenuEntity
import com.asap.asapbackend.infrastructure.jpa.menu.repository.TranslatedMenuJpaRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class MenuTranslateHandler(
    private val textTranslator: TextTranslator,
    private val translatedMenuJpaRepository: TranslatedMenuJpaRepository
) {

    @ApplicationModuleListener
    fun translate(event: MultiMenuCreateEvent) {
        val translatedMenus = event.menus.map {
            val translatedFoods = it.foods.map { food ->
                textTranslator.translate(food.name).translations.map { result ->
                    result.language to FoodEntity(result.text, food.allergies)
                }
            }

            translatedFoods.flatten().groupBy({ it.first }, { it.second }).map { (language, foods) ->
                TranslatedMenuEntity(it.id, language, foods)
            }
        }

        translatedMenuJpaRepository.saveAll(translatedMenus.flatten())
    }
}