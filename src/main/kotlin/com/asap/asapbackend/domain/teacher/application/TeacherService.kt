package com.asap.asapbackend.domain.teacher.application

import com.asap.asapbackend.domain.classroom.domain.service.ClassModifier
import com.asap.asapbackend.domain.classroom.domain.service.ClassroomReader
import com.asap.asapbackend.domain.school.domain.service.SchoolReader
import com.asap.asapbackend.domain.teacher.application.dto.CreateTeacher
import com.asap.asapbackend.domain.teacher.application.dto.GetTeacherInfo
import com.asap.asapbackend.domain.teacher.application.dto.LoginTeacher
import com.asap.asapbackend.domain.teacher.domain.service.TeacherAppender
import com.asap.asapbackend.domain.teacher.domain.service.TeacherReader
import com.asap.asapbackend.domain.teacher.domain.service.TeacherValidator
import com.asap.asapbackend.global.jwt.util.JwtProvider
import com.asap.asapbackend.global.jwt.vo.Claims
import com.asap.asapbackend.global.security.getTeacherId
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TeacherService(
    private val passwordEncoder: PasswordEncoder, // TODO : 리팩터링
    private val teacherAppender: TeacherAppender,
    private val teacherValidator: TeacherValidator,
    private val teacherReader: TeacherReader,
    private val classroomReader: ClassroomReader,
    private val classModifier: ClassModifier,
    private val jwtProvider: JwtProvider,
    private val schoolReader: SchoolReader
) {

    @Transactional
    fun createTeacher(request: CreateTeacher.Request) {
        val teacher = request.extractTeacher(passwordEncoder::encode).also {
            teacherValidator.validateTeacherCreatable(it.username)
            teacherAppender.appendTeacher(it)
        }
        request.extractClassroom { schoolId, grade, className ->
            val classroom = classroomReader.findByClassInfoAndSchoolId(grade, className, schoolId)
            classroom.addTeacher(teacher)
            classModifier.update(classroom)
        }
    }

    fun loginTeacher(request: LoginTeacher.Request): LoginTeacher.Response{
        val (username, password) = request.convertLoginInfo()
        val teacher = teacherReader.findByUsernameAndPassword(username, password, passwordEncoder::matches)
        return LoginTeacher.Response(
            accessToken = jwtProvider.generateTeacherAccessToken(Claims.TeacherClaims(teacher.id)),
        )
    }

    fun getTeacherInfo(): GetTeacherInfo.Response{
        val teacher = teacherReader.findById(getTeacherId())
        val classroom = classroomReader.findByTeacher(teacher.id)
        val school = schoolReader.findById(classroom.getSchoolId())
        return GetTeacherInfo.Response(
            schoolName = school.name,
            grade = classroom.grade,
            className = classroom.className,
            teacherName = teacher.name
        )
    }
}