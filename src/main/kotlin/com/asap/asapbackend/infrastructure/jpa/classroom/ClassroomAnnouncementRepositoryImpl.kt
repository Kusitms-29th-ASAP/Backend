package com.asap.asapbackend.infrastructure.jpa.classroom

import com.asap.asapbackend.domain.classroom.domain.model.ClassroomAnnouncement
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomAnnouncementRepository
import com.asap.asapbackend.global.util.LanguageExtractor
import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import com.asap.asapbackend.infrastructure.jpa.classroom.repository.ClassroomAnnouncementJpaRepository
import com.asap.asapbackend.infrastructure.jpa.classroom.repository.TranslatedClassroomAnnouncementJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class ClassroomAnnouncementRepositoryImpl(
    private val classroomAnnouncementJpaRepository: ClassroomAnnouncementJpaRepository,
    private val languageExtractor: LanguageExtractor,
    private val translatedClassroomAnnouncementJpaRepository: TranslatedClassroomAnnouncementJpaRepository
) : ClassroomAnnouncementRepository {
    override fun findTopByClassroomIdOrderByCreatedAtDesc(classroomId: Long): ClassroomAnnouncement? {
        return classroomAnnouncementJpaRepository.findTopByClassroomIdOrderByCreatedAtDesc(classroomId)?.let {
            translatedClassroomAnnouncementJpaRepository.findByIdOrNull(
                MultiLanguageId(
                    it.id,
                    languageExtractor.getRequestLanguage()
                )
            )?.let { translated ->
                it.updateDescriptions(translated.descriptions)
            }
            ClassroomMapper.toClassroomAnnouncementModel(it)
        }
    }

    override fun findAllByClassroomIdOrderByCreatedAtDesc(classroomId: Long): List<ClassroomAnnouncement> {
        return classroomAnnouncementJpaRepository.findAllByClassroomIdOrderByCreatedAtDesc(classroomId).map {
            translatedClassroomAnnouncementJpaRepository.findByIdOrNull(
                MultiLanguageId(
                    it.id,
                    languageExtractor.getRequestLanguage()
                )
            )?.let { translated ->
                it.updateDescriptions(translated.descriptions)
            }
            ClassroomMapper.toClassroomAnnouncementModel(it)
        }
    }

    override fun findByIdOrNull(classroomAnnouncementId: Long): ClassroomAnnouncement? {
        return classroomAnnouncementJpaRepository.findByIdOrNull(classroomAnnouncementId)?.let {
            translatedClassroomAnnouncementJpaRepository.findByIdOrNull(
                MultiLanguageId(
                    it.id,
                    languageExtractor.getRequestLanguage()
                )
            )?.let { translated ->
                it.updateDescriptions(translated.descriptions)
            }
            ClassroomMapper.toClassroomAnnouncementModel(it)
        }
    }

    override fun save(classroomAnnouncement: ClassroomAnnouncement): ClassroomAnnouncement {
        val entity = ClassroomMapper.toClassroomAnnouncementEntity(classroomAnnouncement)
        val savedEntity = classroomAnnouncementJpaRepository.save(entity)
        return ClassroomMapper.toClassroomAnnouncementModel(savedEntity)
    }
}