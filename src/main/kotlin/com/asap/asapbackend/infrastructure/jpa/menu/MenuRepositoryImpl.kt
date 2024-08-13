package com.asap.asapbackend.infrastructure.jpa.menu

import com.asap.asapbackend.domain.menu.domain.model.Menu
import com.asap.asapbackend.domain.menu.domain.repository.MenuRepository
import com.asap.asapbackend.global.util.LanguageExtractor
import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import com.asap.asapbackend.infrastructure.jpa.menu.entity.MenuEntity
import com.asap.asapbackend.infrastructure.jpa.menu.repository.MenuJpaRepository
import com.asap.asapbackend.infrastructure.jpa.menu.repository.TranslatedMenuJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class MenuRepositoryImpl(
    private val menuJpaRepository: MenuJpaRepository,
    private val translatedMenuJpaRepository: TranslatedMenuJpaRepository,
    private val languageExtractor: LanguageExtractor
) : MenuRepository {
    override fun findBySchoolIdAndDay(schoolId: Long, day: LocalDate): Menu? {
        val menu = menuJpaRepository.findBySchoolIdAndDay(schoolId, day) ?: return null
        val targetLanguageMenuId = getMenusMultiLanguageId(menu)
        translatedMenuJpaRepository.findByIdOrNull(targetLanguageMenuId)?.let {
            menu.changeFoods(it.foods)
        }
        return MenuMapper.toModel(menu)
    }



    override fun findBySchoolIdAndDayInOrderByDayAsc(schoolId: Long, day: List<LocalDate>): List<Menu> {
        val menus = menuJpaRepository.findBySchoolIdAndDayInOrderByDayAsc(schoolId, day)
        val menuFoods =
            translatedMenuJpaRepository.findAllByIdIn(getMenusMultiLanguageIds(menus))
                .associate { it.id.id to it.foods }
        return menus.map {
            it.changeFoods(menuFoods[it.id])
            MenuMapper.toModel(it)
        }
    }

    override fun saveAll(menus: List<Menu>) : List<Menu>{
        val savedMenus = menuJpaRepository.saveAll(menus.map { MenuMapper.toEntity(it) })
        return savedMenus.map {
            MenuMapper.toModel(it)
        }
    }

    private fun getMenusMultiLanguageId(menu: MenuEntity): MultiLanguageId {
        val targetLanguageMenuId = MultiLanguageId(menu.id, languageExtractor.getRequestLanguage())
        return targetLanguageMenuId
    }

    private fun getMenusMultiLanguageIds(menus: List<MenuEntity>): List<MultiLanguageId> {
        return menus.map {
            MultiLanguageId(it.id, languageExtractor.getRequestLanguage())
        }
    }
}