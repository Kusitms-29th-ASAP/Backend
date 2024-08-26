package com.asap.asapbackend.infrastructure.jpa.timetable.handler

import com.asap.asapbackend.domain.timetable.event.MultiSubjectCreateEvent
import com.asap.asapbackend.global.util.TextTranslator
import com.asap.asapbackend.infrastructure.jpa.timetable.entity.TranslatedSubjectEntity
import com.asap.asapbackend.infrastructure.jpa.timetable.repository.TranslatedSubjectJpaRepository
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component

@Component
class SubjectTranslateHandler(
    private val textTranslator: TextTranslator,
    private val translatedSubjectJpaRepository: TranslatedSubjectJpaRepository
) {

    @ApplicationModuleListener
    fun translate(event: MultiSubjectCreateEvent) {
        val translatedSubjects = event.subjects.map { subject ->
            val translatedNames = textTranslator.translate(subject.name).translations.map { result ->
                result.language to result.text
            }

            translatedNames.map { (language, name) ->
                TranslatedSubjectEntity(subject.id, language, name)
            }
        }.flatten()

        translatedSubjectJpaRepository.saveAll(translatedSubjects)
    }
}