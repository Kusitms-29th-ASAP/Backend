package com.asap.asapbackend.global.jwt

import com.asap.asapbackend.fixture.generateFixture
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.mockk.every
import io.mockk.mockk
import java.util.concurrent.CountDownLatch

class JwtProviderConcurrencyTest : FunSpec({
    val jwtProperties = JwtProperties(
        secret = "testSecrettestSecrettestSecrettestSecrettestSecret",
        accessTokenExpirationTime = 10000L,
        refreshTokenExpirationTime = 10000L,
        registrationTokenExpirationTime = 10000L
    )
    val jwtKeyFactory = JwtKeyFactory(jwtProperties)
    val jwtValidator = mockk<JwtValidator>(relaxed = true)
    val jwtRegistry = mockk<JwtRegistry>(relaxed = true)

    val jwtProvider = JwtProvider(jwtProperties, jwtKeyFactory, jwtValidator, jwtRegistry)


    test("동시에 refresh token을 재발급 받을 때, 동일한 토큰이 발급되어야 한다.") {
        // given
        val reissueTokenList = mutableListOf<Token>()
        val countDownLatch = CountDownLatch(10)
        val refreshToken : String= generateFixture()
        val user: Claims.UserClaims = generateFixture()
        every { jwtProvider.extractUserClaimsFromToken(refreshToken, TokenType.REFRESH_TOKEN) } returns user
        every { jwtRegistry.isExists(refreshToken) } returns true
        // when
        (0..9).map {
            Thread {
                Thread.sleep(10)
                val result = jwtProvider.reissueToken(refreshToken)
                reissueTokenList.add(result)
                countDownLatch.countDown()
            }.start()
        }
        countDownLatch.await()

        //then
        val firstToken = reissueTokenList[0]
        reissueTokenList.forEach {
            it shouldBeEqualToComparingFields firstToken
        }

    }
})