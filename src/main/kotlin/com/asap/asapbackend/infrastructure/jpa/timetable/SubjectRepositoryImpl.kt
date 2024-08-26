package com.asap.asapbackend.infrastructure.jpa.timetable

import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.timetable.domain.model.Subject
import com.asap.asapbackend.domain.timetable.domain.repository.SubjectRepository
import com.asap.asapbackend.global.util.LanguageExtractor
import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import com.asap.asapbackend.infrastructure.jpa.timetable.TimetableMapper.toSubjectModel
import com.asap.asapbackend.infrastructure.jpa.timetable.repository.SubjectJpaRepository
import com.asap.asapbackend.infrastructure.jpa.timetable.repository.TranslatedSubjectJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class SubjectRepositoryImpl(
    private val subjectJpaRepository: SubjectJpaRepository,
    private val translatedSubjectJpaRepository: TranslatedSubjectJpaRepository,
    private val languageExtractor: LanguageExtractor
) : SubjectRepository {
    override fun findByClassroomIn(classroom: List<Classroom>): List<Subject> {
        return subjectJpaRepository.findAllByClassroomIdIn(classroom.map { it.id })
            .map {
                val translatedSubject = translatedSubjectJpaRepository.findByIdOrNull(
                    MultiLanguageId(
                        it.id,
                        languageExtractor.getRequestLanguage()
                    )
                )
                it.changeName(translatedSubject?.name)
                toSubjectModel(it)
            }
    }


    override fun saveAll(subjects: List<Subject>): List<Subject> {
        val savedSubjects = subjectJpaRepository.saveAll(subjects.map { TimetableMapper.toSubjectEntity(it) })
        return savedSubjects.map { toSubjectModel(it) }
    }

    override fun findOriginalSubjectsByClassroomIn(classroom: List<Classroom>): List<Subject> {
        return subjectJpaRepository.findAllByClassroomIdIn(classroom.map { it.id })
            .map { toSubjectModel(it) }
    }

    override fun findAllByClassroomId(classroomId: Long): List<Subject> {
        return subjectJpaRepository.findAllByClassroomId(classroomId)
            .map {
                val translatedSubject = translatedSubjectJpaRepository.findByIdOrNull(
                    MultiLanguageId(
                        it.id,
                        languageExtractor.getRequestLanguage()
                    )
                )

                it.changeName(translatedSubject?.name)
                toSubjectModel(it)
            }
    }

    override fun save(subject: Subject): Subject {
        val savedSubject = subjectJpaRepository.save(TimetableMapper.toSubjectEntity(subject))
        return toSubjectModel(savedSubject)
    }
}