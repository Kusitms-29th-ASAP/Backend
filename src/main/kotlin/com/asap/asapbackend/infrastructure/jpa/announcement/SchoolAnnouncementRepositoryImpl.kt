package com.asap.asapbackend.infrastructure.jpa.announcement

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementRepository
import com.asap.asapbackend.global.util.LanguageExtractor
import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import com.asap.asapbackend.infrastructure.jpa.announcement.repository.SchoolAnnouncementJpaRepository
import com.asap.asapbackend.infrastructure.jpa.announcement.repository.TranslatedSchoolAnnouncementJpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class SchoolAnnouncementRepositoryImpl(
    private val schoolAnnouncementJpaRepository: SchoolAnnouncementJpaRepository,
    private val translatedSchoolAnnouncementJpaRepository: TranslatedSchoolAnnouncementJpaRepository,
    private val languageExtractor: LanguageExtractor
): SchoolAnnouncementRepository {
    override fun findLastIndex(schoolId: Long): Int {
        return schoolAnnouncementJpaRepository.findLastIndex(schoolId)
    }

    override fun findAllByClassroomId(classroomId: Long, pageable: Pageable): Page<SchoolAnnouncement> {
        return schoolAnnouncementJpaRepository.findAllByClassroomId(classroomId, pageable).map {
            val translated = translatedSchoolAnnouncementJpaRepository.findByIdOrNull(
                MultiLanguageId(it.id, languageExtractor.getRequestLanguage())
            )
            it.changeKeywords(translated?.keywords ?: it.keywords)
            it.changeSummaries(translated?.summaries ?: it.summaries)
            it.changeTitle(translated?.title ?: it.title)
            AnnouncementMapper.toModel(it)
        }
    }

    override fun insertBatch(announcements: Set<SchoolAnnouncement>): Set<SchoolAnnouncement> {
        val announcementEntities = announcements.map { AnnouncementMapper.toEntity(it) }
        val savedAnnouncements = schoolAnnouncementJpaRepository.saveAll(announcementEntities)
        return savedAnnouncements.map {
            AnnouncementMapper.toModel(it)
        }.toSet()
    }

    override fun findByIdOrNull(id: Long): SchoolAnnouncement? {
        return schoolAnnouncementJpaRepository.findByIdOrNull(id)?.let {
            val translated = translatedSchoolAnnouncementJpaRepository.findByIdOrNull(
                MultiLanguageId(it.id, languageExtractor.getRequestLanguage())
            )
            it.changeKeywords(translated?.keywords ?: it.keywords)
            it.changeSummaries(translated?.summaries ?: it.summaries)
            it.changeTitle(translated?.title ?: it.title)
            AnnouncementMapper.toModel(it)
        }
    }
}