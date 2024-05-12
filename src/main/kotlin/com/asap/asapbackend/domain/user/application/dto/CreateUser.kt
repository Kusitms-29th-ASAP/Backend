package com.asap.asapbackend.domain.user.application.dto

import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.domain.child.domain.model.Gender
import com.asap.asapbackend.domain.classroom.domain.vo.Grade
import com.asap.asapbackend.domain.menu.domain.model.Allergy
import com.asap.asapbackend.domain.user.domain.model.Agreement
import com.asap.asapbackend.domain.user.domain.model.PhoneNumber
import com.asap.asapbackend.domain.user.domain.model.SocialInfo
import com.asap.asapbackend.domain.user.domain.model.User
import com.asap.asapbackend.global.jwt.vo.Claims
import java.time.LocalDate

class CreateUser {

    data class Request(
        val registrationToken: String,
        val agreement: Agreement,
        val phoneNumber: PhoneNumber, // 객체로 생성해서 관리해야함
        val children: List<ChildDetail>
    ){
        fun extractUser(registrationExtractor: (String) -> Claims.RegistrationClaims): User {
            return User(
                registrationExtractor(registrationToken).let { SocialInfo(it.socialId, it.provider) },
                phoneNumber,
                agreement
            )
        }

        fun performActionOnChildren(action: (ChildDetail) -> Unit){
            children.forEach {
                action(it)
            }
        }

    }

    data class ChildDetail(
        val name: String,
        val gender: Gender,
        val birth: LocalDate,
        val elementSchoolId: Long,
        val elementSchoolGrade: Grade?,
        val elementSchoolClassNumber: String?,
        val elementSchoolClassCode: String?,
        val allergies: Set<Allergy>
    ){
        fun extractChild(user: User): Child {
            return Child(
                name,
                gender,
                birth,
                allergies,
                user
            )
        }

        fun performActionOnClassroom(action:(Grade?, String?, String?, Long) -> Unit) {
            action(elementSchoolGrade, elementSchoolClassNumber, elementSchoolClassCode,elementSchoolId)
        }
    }

    data class Response(
        val accessToken: String,
        val refreshToken: String
    )

}