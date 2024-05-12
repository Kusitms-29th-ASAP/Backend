package com.asap.asapbackend.domain.user.application

import com.asap.asapbackend.domain.child.domain.service.ChildAppender
import com.asap.asapbackend.domain.classroom.domain.model.Classroom
import com.asap.asapbackend.domain.classroom.domain.service.ClassModifier
import com.asap.asapbackend.domain.classroom.domain.service.ClassroomReader
import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.domain.user.application.dto.CreateUser
import com.asap.asapbackend.domain.user.domain.model.Agreement
import com.asap.asapbackend.domain.user.domain.service.UserAppender
import com.asap.asapbackend.fixture.generateFixture
import com.asap.asapbackend.fixture.user.MockUserAppender
import com.asap.asapbackend.global.jwt.util.JwtProvider
import com.asap.asapbackend.global.jwt.util.TokenExtractor
import com.asap.asapbackend.global.jwt.vo.Claims
import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

private val logger = KotlinLogging.logger {}

class UserServiceTest:BehaviorSpec({
    val jwtProvider: JwtProvider = mockk()
    val tokenExtractor: TokenExtractor = mockk()
    val userAppender: UserAppender = MockUserAppender()
    val classroomReader: ClassroomReader = mockk()
    val childAppender: ChildAppender = mockk(relaxed = true)
    val classModifier: ClassModifier = mockk(relaxed = true)

    val userService = UserService(jwtProvider, tokenExtractor, userAppender, classroomReader, childAppender, classModifier)


    given("UserService.createUser") {
        val registrationToken : String = generateFixture()
        val userCreateRequest : CreateUser.Request = generateFixture {
            it.set("phoneNumber.number", "01012345678")
            it.set("agreement", Agreement(true,true,true))
        }
        val registrationClaims : Claims.RegistrationClaims= generateFixture()
        val classroom = Classroom(
            grade = 5,
            className = "5-1",
            school = School("school1", "school1", "school1", "school1")
        )
        val accessToken : String = generateFixture()
        val refreshToken : String = generateFixture()
        every { tokenExtractor.extractValue(userCreateRequest.registrationToken) } returns registrationToken
        every { jwtProvider.extractRegistrationClaimsFromToken(registrationToken) } returns registrationClaims
        every { classroomReader.findByClassInfoAndSchoolId(any(), any(), any(), any()) } returns classroom
        every { jwtProvider.generateAccessToken(any()) } returns accessToken
        every { jwtProvider.generateRefreshToken(any()) } returns refreshToken

        `when`("request가 주어지면") {
            val response = userService.createUser(userCreateRequest)
            then("user를 생성하고, children을 생성하고, classroom에 추가하고, token을 발급한다") {
                response.accessToken shouldBe accessToken
                response.refreshToken shouldBe refreshToken
                verify { userAppender.appendUser(any()) }
                verify(exactly = userCreateRequest.children.size) {
                    childAppender.appendChild(any())
                    classModifier.update(any())
                }
            }
        }
    }
})


