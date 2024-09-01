package com.asap.asapbackend.infrastructure.jpa.announcement.handler

import com.asap.asapbackend.domain.announcement.event.MultiEducationOfficeAnnouncementCreateEvent
import com.asap.asapbackend.domain.announcement.event.MultiSchoolAnnouncementCreateEvent
import com.asap.asapbackend.global.util.TextTranslator
import com.asap.asapbackend.infrastructure.jpa.announcement.entity.TranslatedEducationAnnouncementEntity
import com.asap.asapbackend.infrastructure.jpa.announcement.entity.TranslatedSchoolAnnouncementEntity
import com.asap.asapbackend.infrastructure.jpa.announcement.repository.TranslatedEducationAnnouncementJpaRepository
import com.asap.asapbackend.infrastructure.jpa.announcement.repository.TranslatedSchoolAnnouncementJpaRepository
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component

@Component
class AnnouncementTranslateHandler(
    private val textTranslator: TextTranslator,
    private val translatedEducationAnnouncementJpaRepository: TranslatedEducationAnnouncementJpaRepository,
    private val translatedSchoolAnnouncementJpaRepository: TranslatedSchoolAnnouncementJpaRepository
) {

    @ApplicationModuleListener
    fun translateEducationAnnouncement(events: MultiEducationOfficeAnnouncementCreateEvent){
        val translatedAnnouncement = events.announcements.map {
            val translatedTitle = textTranslator.translate(it.title).translations.map { result ->
                result.language to result.text
            }.toMap()

            val translatedKeywords = it.keywords.map { keyword ->
                textTranslator.translate(keyword).translations.map { result ->
                    result.language to result.text
                }
            }.flatten()

            val keywordsMap = translatedKeywords.groupBy({ it.first }, { it.second })


            val translatedSummaries = it.summaries.map { summary ->
                textTranslator.translate(summary).translations.map { result ->
                    result.language to result.text
                }
            }.flatten()

            val summariesMap = translatedSummaries.groupBy({ it.first }, { it.second })

            keywordsMap.keys.map { language ->
                val keywords = keywordsMap[language] ?: emptyList()
                val summaries = summariesMap[language] ?: emptyList()
                val title = translatedTitle[language] ?: it.title
                TranslatedEducationAnnouncementEntity(it.id, language, title, summaries, keywords)
            }
        }.flatten()
        translatedEducationAnnouncementJpaRepository.saveAll(translatedAnnouncement)
    }

    @ApplicationModuleListener
    fun translateSchoolAnnouncement(events: MultiSchoolAnnouncementCreateEvent){
        val translatedAnnouncement = events.announcements.map {
            val translatedTitle = textTranslator.translate(it.title).translations.map { result ->
                result.language to result.text
            }.toMap()

            val translatedKeywords = it.keywords.map { keyword ->
                textTranslator.translate(keyword).translations.map { result ->
                    result.language to result.text
                }
            }.flatten()

            val keywordsMap = translatedKeywords.groupBy({ it.first }, { it.second })


            val translatedSummaries = it.summaries.map { summary ->
                textTranslator.translate(summary).translations.map { result ->
                    result.language to result.text
                }
            }.flatten()

            val summariesMap = translatedSummaries.groupBy({ it.first }, { it.second })

            keywordsMap.keys.map { language ->
                val keywords = keywordsMap[language] ?: emptyList()
                val summaries = summariesMap[language] ?: emptyList()
                val title = translatedTitle[language] ?: it.title
                TranslatedSchoolAnnouncementEntity(it.id, language, title, summaries, keywords)
            }
        }.flatten()
        translatedSchoolAnnouncementJpaRepository.saveAll(translatedAnnouncement)
    }


}