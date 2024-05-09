package com.asap.asapbackend.domain.user.application

import com.asap.asapbackend.domain.child.domain.service.ChildAppender
import com.asap.asapbackend.domain.classroom.domain.service.ClassroomReader
import com.asap.asapbackend.domain.user.application.dto.CreateUser
import com.asap.asapbackend.domain.user.domain.service.UserAppender
import com.asap.asapbackend.global.jwt.vo.Claims
import com.asap.asapbackend.global.jwt.util.JwtProvider
import com.asap.asapbackend.global.jwt.util.TokenExtractor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val jwtProvider: JwtProvider,
    private val tokenExtractor: TokenExtractor,
    private val userAppender: UserAppender,
    private val classroomReader: ClassroomReader,
    private val childAppender: ChildAppender,
) {

    @Transactional
    fun createUser(request: CreateUser.Request): CreateUser.Response {
        val user = request.extractUser {
            val registrationToken = tokenExtractor.extractValue(it)
            val claims: Claims.RegistrationClaims = jwtProvider.extractRegistrationClaimsFromToken(registrationToken)
            return@extractUser claims
        }.apply { userAppender.appendUser(this) }

        request.performActionOnChildren {
            val child = it.extractChild(user).apply { childAppender.appendChild(this) }
            it.performActionOnClassroom { grade, className, classCode, schoolId ->
                val classroom = classroomReader.findByClassInfoAndSchoolId(grade, className, classCode, schoolId)
                classroom.addChild(child)
            }
        }
        val userClaims = Claims.UserClaims(user.id)
        return CreateUser.Response(
            jwtProvider.generateAccessToken(userClaims),
            jwtProvider.generateRefreshToken(userClaims)
        )
    }
}