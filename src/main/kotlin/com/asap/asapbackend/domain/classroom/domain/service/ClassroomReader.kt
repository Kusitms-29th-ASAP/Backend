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
    // TODO : 예외 정의하기
    fun findByClassInfoAndSchoolId(grade: Grade?, classNumber: String?, classCode: String?,schoolId: Long): Classroom {
        if((grade == null || classNumber == null) && classCode == null) throw ClassroomException(ClassroomErrorCode.CLASS_NOT_FOUND)

        return findClassroom {
            classCode?.let {
                classroomRepository.findByClassCode(classCode)
            } ?: classroomRepository.findByGradeAndClassNameAndSchoolId(grade!!, classNumber!!, schoolId)
        }
    }

    fun findByClassInfoAndSchoolName(grade: Grade, className: String,schoolName: String): Classroom {
        return findClassroom {
            classroomRepository.findByGradeAndClassNameAndSchoolName(grade, className, schoolName)
        }
    }

    // TODO : 예외 정의하기
    private fun findClassroom(function : () -> Classroom?):Classroom{
        return function()?:throw ClassroomNotFoundException(ClassroomErrorCode.CLASS_NOT_FOUND)
    }
}