package com.asap.asapbackend.infrastructure.jpa.announcement

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import com.asap.asapbackend.domain.announcement.domain.repository.EducationOfficeAnnouncementRepository
import com.asap.asapbackend.global.util.LanguageExtractor
import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import com.asap.asapbackend.infrastructure.jpa.announcement.repository.EducationAnnouncementJpaRepository
import com.asap.asapbackend.infrastructure.jpa.announcement.repository.TranslatedEducationAnnouncementJpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class EducationOfficeAnnouncementRepositoryImpl(
    private val educationAnnouncementJpaRepository: EducationAnnouncementJpaRepository,
    private val translatedEducationAnnouncementJpaRepository: TranslatedEducationAnnouncementJpaRepository,
    private val languageExtractor: LanguageExtractor
) : EducationOfficeAnnouncementRepository {
    override fun findLastIndex(): Int {
        return educationAnnouncementJpaRepository.findLastIndex()
    }

    override fun insertBatch(announcements: Set<EducationOfficeAnnouncement>): Set<EducationOfficeAnnouncement> {
        val announcementEntities = announcements.map { AnnouncementMapper.toEntity(it) }
        val savedAnnouncements = educationAnnouncementJpaRepository.saveAll(announcementEntities)
        return savedAnnouncements.map {
            AnnouncementMapper.toModel(it)
        }.toSet()
    }

    override fun findAll(pageable: Pageable): Page<EducationOfficeAnnouncement> {
        val entities = educationAnnouncementJpaRepository.findAll(pageable)
        return entities.map {
            val translated = translatedEducationAnnouncementJpaRepository.findByIdOrNull(
                MultiLanguageId(it.id, languageExtractor.getRequestLanguage())
            )
            it.changeKeywords(translated?.keywords ?: it.keywords)
            it.changeSummaries(translated?.summaries ?: it.summaries)
            it.changeTitle(translated?.title ?: it.title)
            AnnouncementMapper.toModel(it)
        }
    }

    override fun findByIdOrNull(id: Long): EducationOfficeAnnouncement? {
        return educationAnnouncementJpaRepository.findByIdOrNull(id)?.let {
            val translated = translatedEducationAnnouncementJpaRepository.findByIdOrNull(
                MultiLanguageId(it.id, languageExtractor.getRequestLanguage())
            )
            it.changeKeywords(translated?.keywords ?: it.keywords)
            it.changeSummaries(translated?.summaries ?: it.summaries)
            it.changeTitle(translated?.title ?: it.title)
            AnnouncementMapper.toModel(it)
        }
    }
}