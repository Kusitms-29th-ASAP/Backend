package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.domain.classroom.domain.exception.ClassroomErrorCode
import com.asap.asapbackend.domain.classroom.domain.exception.ClassroomException
import com.asap.asapbackend.domain.classroom.domain.exception.ClassroomNotFoundException
import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.model.Grade
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomRepository
import org.springframework.stereotype.Service

@Service
class ClassroomReader(
    private val classroomRepository: ClassroomRepository
) {

    fun findByClassInfoAndSchoolId(grade: Grade?, classNumber: String?, classCode: String?, schoolId: Long): Classroom {
        return findClassroom {
            if (grade != null && classNumber != null) {
                classroomRepository.findByGradeAndClassNameAndSchoolId(grade, classNumber, schoolId)
            } else if (classCode != null) {
                classroomRepository.findByClassCode(classCode)
            } else {
                throw ClassroomException(ClassroomErrorCode.CLASS_NOT_FOUND)
            }
        }
    }

    fun findByClassInfoAndSchoolName(grade: Grade, className: String, schoolName: String): Classroom {
        return findClassroom {
            classroomRepository.findByGradeAndClassNameAndSchoolName(grade, className, schoolName)
        }
    }

    private fun findClassroom(function: () -> Classroom?): Classroom {
        return function() ?: throw ClassroomNotFoundException(ClassroomErrorCode.CLASS_NOT_FOUND)
    }
}