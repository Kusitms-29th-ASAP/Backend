package com.asap.asapbackend.global.jwt

import com.asap.asapbackend.fixture.generateFixture
import com.asap.asapbackend.generateBasicParser
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.concurrent.CountDownLatch

class JwtProviderTest : BehaviorSpec({
    val jwtProperties = JwtProperties(
        secret = "testSecrettestSecrettestSecrettestSecrettestSecret",
        accessTokenExpirationTime = 10000L,
        refreshTokenExpirationTime = 1000000L
    )
    val jwtKeyFactory = JwtKeyFactory(jwtProperties)
    val jwtValidator = mockk<JwtValidator>(relaxed = true)

    val jwtProvider = JwtProvider(jwtProperties, jwtKeyFactory, jwtValidator)

    given("토큰을 생성하는 과정에서") {
        val user: Claims.UserClaims = generateFixture()
        val parser = generateBasicParser(
            jwtKeyFactory.generateKey(),
            PrivateClaims.retrieveClaimsClassType()
        )
        `when`("generateAccessToken을 호출하면") {
            val result = jwtProvider.generateAccessToken(user)
            then("accessToken이 생성되어야 한다.") {
                println(result)
                val payload = parser.parseSignedClaims(result).payload
                payload.issuer shouldBe JwtConst.TOKEN_ISSUER
                payload.get(JwtConst.USER_CLAIMS, Claims.UserClaims::class.java)
                    .shouldBeEqualUsingFields(user)
                payload.get(JwtConst.TOKEN_TYPE, TokenType::class.java) shouldBe TokenType.ACCESS_TOKEN
            }
        }

        `when`("generateRefreshToken을 호출하면") {
            val result = jwtProvider.generateRefreshToken(user)
            then("refreshToken이 생성되어야 한다.") {
                val payload = parser.parseSignedClaims(result).payload
                payload.issuer shouldBe JwtConst.TOKEN_ISSUER
                payload.get(JwtConst.USER_CLAIMS, Claims.UserClaims::class.java)
                    .shouldBeEqualUsingFields(user)
                payload.get(JwtConst.TOKEN_TYPE, TokenType::class.java) shouldBe TokenType.REFRESH_TOKEN
            }
        }

        val registrationClaims : Claims.RegistrationClaims = generateFixture()
        `when`("generateRegistrationToken을 호출하면") {
            val result = jwtProvider.generateRegistrationToken(registrationClaims)
            println(result)
            then("registrationToken이 생성되어야 한다.") {
                val payload = parser.parseSignedClaims(result).payload
                payload.issuer shouldBe JwtConst.TOKEN_ISSUER
                payload.get(JwtConst.REGISTRATION_CLAIMS, Claims.RegistrationClaims::class.java)
                    .shouldBeEqualUsingFields(registrationClaims)
                payload.get(JwtConst.TOKEN_TYPE, TokenType::class.java) shouldBe TokenType.REGISTRATION_TOKEN
            }
        }
    }

    given("토큰 재발급 과정에서") {
        val refreshToken: String = generateFixture()
        val user: Claims.UserClaims = generateFixture()
        println(user.toString())
        every { jwtProvider.extractUserClaimsFromToken(refreshToken, TokenType.REFRESH_TOKEN) } returns user
        val parser = generateBasicParser(
            jwtKeyFactory.generateKey(),
            PrivateClaims.retrieveClaimsClassType()
        )
        `when`("reissueToken을 호출하면") {
            val result = jwtProvider.reissueToken(refreshToken)
            val generatedAccessToken = result.accessToken
            val generatedRefreshToken = result.refreshToken
            val accessTokenPayload = parser.parseSignedClaims(generatedAccessToken).payload
            val refreshTokenPayload = parser.parseSignedClaims(generatedRefreshToken).payload
            then("accessToken과 refreshToken이 생성되어야 한다.") {
                // accessToken
                accessTokenPayload.issuer shouldBe JwtConst.TOKEN_ISSUER
                accessTokenPayload.get(JwtConst.USER_CLAIMS, Claims.UserClaims::class.java)
                    .shouldBeEqualUsingFields(user)
                accessTokenPayload.get(JwtConst.TOKEN_TYPE, TokenType::class.java) shouldBe TokenType.ACCESS_TOKEN
                // refreshToken
                refreshTokenPayload.issuer shouldBe JwtConst.TOKEN_ISSUER
                refreshTokenPayload.get(JwtConst.USER_CLAIMS, Claims.UserClaims::class.java)
                    .shouldBeEqualUsingFields(user)
                refreshTokenPayload.get(JwtConst.TOKEN_TYPE, TokenType::class.java) shouldBe TokenType.REFRESH_TOKEN
            }
        }

        `when`("동시에 reissueToken을 호출하면") {
            val reissueTokenList = mutableListOf<Token>()
            val countDownLatch = CountDownLatch(10)
            (0..9).map {
                Thread {
                    Thread.sleep(10)
                    val result = jwtProvider.reissueToken(refreshToken)
                    reissueTokenList.add(result)
                    countDownLatch.countDown()
                }.start()
            }
            countDownLatch.await()
            then("동일한 토큰이 반환되어야 한다.") {
                val firstToken = reissueTokenList[0]
                reissueTokenList.forEach {
                    it shouldBeEqualToComparingFields firstToken
                }
            }
        }
    }

    given("토큰 추출 과정에서") {
        val user: Claims.UserClaims = generateFixture()
        val token: String = jwtProvider.generateAccessToken(user)
        every { jwtValidator.initializeJwtParser(TokenType.ACCESS_TOKEN) } returns generateBasicParser(
            jwtKeyFactory.generateKey(),
            PrivateClaims.retrieveClaimsClassType()
        )
        `when`("extractUserClaimsFromToken을 호출하면") {
            val result: Claims.UserClaims = jwtProvider.extractUserClaimsFromToken(token, TokenType.ACCESS_TOKEN)
            then("토큰에서 추출한 userClaims가 반환되어야 한다.") {
                result.shouldBeEqualUsingFields(user)
            }
        }

        `when`( "extractRegistrationClaimsFromToken을 호출하면"){
            val registrationClaims : Claims.RegistrationClaims = generateFixture()
            val registrationToken : String = jwtProvider.generateRegistrationToken(registrationClaims)
            every { jwtValidator.initializeJwtParser(TokenType.REGISTRATION_TOKEN) } returns generateBasicParser(
                jwtKeyFactory.generateKey(),
                PrivateClaims.retrieveClaimsClassType()
            )
            val result : Claims.RegistrationClaims = jwtProvider.extractRegistrationClaimsFromToken(registrationToken, TokenType.REGISTRATION_TOKEN)
            then("토큰에서 추출한 registrationClaims가 반환되어야 한다."){
                result.shouldBeEqualUsingFields(registrationClaims)
            }
        }


    }

})