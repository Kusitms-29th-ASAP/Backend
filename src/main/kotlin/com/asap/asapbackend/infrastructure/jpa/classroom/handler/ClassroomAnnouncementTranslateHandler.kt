package com.asap.asapbackend.infrastructure.jpa.classroom.handler

import com.asap.asapbackend.domain.classroom.event.ClassroomAnnouncementCreateEvent
import com.asap.asapbackend.global.util.TextTranslator
import com.asap.asapbackend.infrastructure.jpa.classroom.entity.TranslatedClassroomAnnouncementEntity
import com.asap.asapbackend.infrastructure.jpa.classroom.repository.TranslatedClassroomAnnouncementJpaRepository
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component

@Component
class ClassroomAnnouncementTranslateHandler(
    private val textTranslator: TextTranslator,
    private val translatedClassroomAnnouncementJpaRepository: TranslatedClassroomAnnouncementJpaRepository
) {

    @ApplicationModuleListener
    fun translate(event: ClassroomAnnouncementCreateEvent) {
        val translated = event.classroomAnnouncement.descriptions.map { description ->
            textTranslator.translate(description.description).translations.map { result ->
                result.language to description.copy(description = result.text)
            }
        }.flatten()

        val translatedClassroomAnnouncement =
            translated.groupBy({ it.first }, { it.second })
                .map { (language, descriptions) ->
                    TranslatedClassroomAnnouncementEntity(event.classroomAnnouncement.id, language, descriptions)
                }

        translatedClassroomAnnouncementJpaRepository.saveAll(translatedClassroomAnnouncement)
    }
}