package com.asap.asapbackend.domain.user.domain.model

import com.asap.asapbackend.global.exception.DefaultException
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class PhoneNumberTest : FunSpec({
    test("전화번호가 11자리 숫자로 구성되면 객체 생성이 된다.") {
        val number = "01012345678"
        shouldNotThrow<Exception> {
            val phoneNumber = PhoneNumber(number)
            phoneNumber.number shouldBe number
        }
    }

    test("전화번호가 11자리 이상일 때 IllegalPropertyException이 발생한다.") {
        shouldThrow<DefaultException.IllegalPropertyException> {
            PhoneNumber("0101234567123123")
        }
    }

    test("전화번호가 11자리 미만일 때 IllegalPropertyException이 발생한다.") {
        shouldThrow<DefaultException.IllegalPropertyException> {
            PhoneNumber("0101234567")
        }
    }

    test("전화번호가 숫자가 아닌 문자를 포함하고 있을 때 IllegalPropertyException이 발생한다.") {
        shouldThrow<DefaultException.IllegalPropertyException> {
            PhoneNumber("0101234567a")
        }
    }
})