package com.asap.asapbackend.domain.classroom.domain.service

import com.asap.asapbackend.domain.child.domain.model.Child
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

    fun findByClassInfoAndSchoolId(grade: Grade, className: String, schoolId: Long): Classroom {
        return findClassroom {
            classroomRepository.findByGradeAndClassNameAndSchoolId(grade, className, schoolId)
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

    fun findClassroomMapByChild(child: List<Child>): Map<Child, Classroom> {
        return classroomRepository.findAllByChildIds(child.map { it.id }).flatMap {classroom->
            child.map {
                it to classroom
            }.filter { (child, classroom)->
                classroom.childClassroomSet.any { it.student.id == child.id }
            }
        }.toMap()
    }


    fun findBySchoolId(schoolId: Long): List<Classroom> {
        return classroomRepository.findAllBySchoolId(schoolId)
    }

    private fun findClassroom(function: () -> Classroom?): Classroom {
        return function() ?: throw ClassroomException.ClassroomNotFoundException()
    }
}