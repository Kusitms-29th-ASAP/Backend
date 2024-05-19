package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.domain.classroom.domain.exception.ClassroomException
import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomRepository
import com.asap.asapbackend.domain.classroom.domain.vo.Grade
import org.springframework.stereotype.Service

@Service
class ClassroomReader(
    private val classroomRepository: ClassroomRepository,
) {

    fun findByClassInfoAndSchoolId(grade: Grade?, classNumber: String?, classCode: String?, schoolId: Long): Classroom {
        return findClassroom {
            if (grade != null && classNumber != null) {
                classroomRepository.findByGradeAndClassNameAndSchoolId(grade, classNumber, schoolId)
            } else if (classCode != null) {
                classroomRepository.findByClassCode(classCode)
            } else {
                throw ClassroomException.ClassroomNotFoundException()
            }
        }
    }

    fun findByClassInfoAndSchoolName(grade: Grade, className: String, schoolName: String): Classroom {
        return findClassroom {
            classroomRepository.findByGradeAndClassNameAndSchoolName(grade, className, schoolName)
        }
    }

    fun findByTeacher(teacherId: Long): Classroom {
        return findClassroom {
            classroomRepository.findByTeacherId(teacherId)
        }
    }

    fun findByStudent(studentId: Long): Classroom {
        return findClassroom {
            classroomRepository.findByStudentId(studentId)
        }
    }

    fun findClassroomMapByStudentId(studentIds: List<Long>): Map<Long, Classroom> {
        return classroomRepository.findAllByStudentIds(studentIds).flatMap {classroom->
            classroom.childClassroomSet.map {
                it.student.id to classroom
            }
        }.toMap()
//        return classroomRepository.findAllByStudentIds(studentIds).flatMap {classroom->
//            classroom.getStudentIds().map {
//                it to classroom
//            }
//        }.toMap()
//        return classroomRepository.findAllByStudentIds(studentIds).associateBy {
//            it.getStudentIds().find { studentId -> studentIds.contains(studentId) }!!
//        }
    }


    private fun findClassroom(function: () -> Classroom?): Classroom {
        return function() ?: throw ClassroomException.ClassroomNotFoundException()
    }
}