package com.asap.asapbackend.global.jwt

import com.asap.asapbackend.fixture.generateFixture
import com.asap.asapbackend.generateBasicParser
import com.asap.asapbackend.global.jwt.exception.TokenException
import com.asap.asapbackend.global.jwt.util.JwtKeyFactory
import com.asap.asapbackend.global.jwt.util.JwtProvider
import com.asap.asapbackend.global.jwt.util.JwtRegistry
import com.asap.asapbackend.global.jwt.util.JwtValidator
import com.asap.asapbackend.global.jwt.vo.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class JwtProviderTest : BehaviorSpec({
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

    isolationMode = IsolationMode.InstancePerTest // 추가

    given("토큰을 생성하는 과정에서") {
        val user: Claims.UserClaims = generateFixture()
        val parser = generateBasicParser(
            jwtKeyFactory.generateKey(),
            PrivateClaims.retrieveClaimsClassType()
        )
        `when`("generateAccessToken을 호출하면") {
            val result = jwtProvider.generateAccessToken(user)
            then("accessToken이 생성되어야 한다.") {
                val payload = parser.parseSignedClaims(result).payload
                payload.issuer shouldBe JwtConst.TOKEN_ISSUER
                payload.get(ClaimsType.USER.claimsKey, Claims.UserClaims::class.java)
                    .shouldBeEqualUsingFields(user)
                payload.get(JwtConst.TOKEN_TYPE, TokenType::class.java) shouldBe TokenType.ACCESS_TOKEN
            }
        }

        `when`("generateRefreshToken을 호출하면") {
            val result = jwtProvider.generateRefreshToken(user)
            then("refreshToken이 생성되어야 한다.") {
                val payload = parser.parseSignedClaims(result).payload
                payload.issuer shouldBe JwtConst.TOKEN_ISSUER
                payload.get(ClaimsType.USER.claimsKey, Claims.UserClaims::class.java)
                    .shouldBeEqualUsingFields(user)
                payload.get(JwtConst.TOKEN_TYPE, TokenType::class.java) shouldBe TokenType.REFRESH_TOKEN
            }
        }

        val registrationClaims : Claims.RegistrationClaims = generateFixture()
        `when`("generateRegistrationToken을 호출하면") {
            val result = jwtProvider.generateRegistrationToken(registrationClaims)
            then("registrationToken이 생성되어야 한다.") {
                val payload = parser.parseSignedClaims(result).payload
                payload.issuer shouldBe JwtConst.TOKEN_ISSUER
                payload.get(ClaimsType.REGISTRATION.claimsKey, Claims.RegistrationClaims::class.java)
                    .shouldBeEqualUsingFields(registrationClaims)
                payload.get(JwtConst.TOKEN_TYPE, TokenType::class.java) shouldBe TokenType.REGISTRATION_TOKEN
            }
        }
    }

    given("토큰 재발급 과정에서") {
        val refreshToken: String = generateFixture()
        val user: Claims.UserClaims = generateFixture()
        every { jwtProvider.extractUserClaimsFromToken(refreshToken, TokenType.REFRESH_TOKEN) } returns user
        val parser = generateBasicParser(
            jwtKeyFactory.generateKey(),
            PrivateClaims.retrieveClaimsClassType()
        )
        `when`("reissueToken을 호출하면 refreshToken이 유효한 경우") {
            every { jwtRegistry.isExists(refreshToken) } returns true
            val (generatedAccessToken, generatedRefreshToken) = jwtProvider.reissueToken(refreshToken)
            val accessTokenPayload = parser.parseSignedClaims(generatedAccessToken).payload
            val refreshTokenPayload = parser.parseSignedClaims(generatedRefreshToken).payload

            then("accessToken과 refreshToken이 생성되어야 한다.") {
                // accessToken
                accessTokenPayload.issuer shouldBe JwtConst.TOKEN_ISSUER
                accessTokenPayload.get(ClaimsType.USER.claimsKey, Claims.UserClaims::class.java)
                    .shouldBeEqualUsingFields(user)
                accessTokenPayload.get(JwtConst.TOKEN_TYPE, TokenType::class.java) shouldBe TokenType.ACCESS_TOKEN
                // refreshToken
                refreshTokenPayload.issuer shouldBe JwtConst.TOKEN_ISSUER
                refreshTokenPayload.get(ClaimsType.USER.claimsKey, Claims.UserClaims::class.java)
                    .shouldBeEqualUsingFields(user)
                refreshTokenPayload.get(JwtConst.TOKEN_TYPE, TokenType::class.java) shouldBe TokenType.REFRESH_TOKEN
            }
        }

        `when`("reissueToken을 호출하면 refreshToken이 유효하지 않은 경우") {
            every { jwtRegistry.isExists(refreshToken) } returns false
            then("TokenNotFoundException이 발생해야 한다.") {
                shouldThrow<TokenException.TokenNotFoundException> {
                    jwtProvider.reissueToken(refreshToken)
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
            val result : Claims.RegistrationClaims = jwtProvider.extractRegistrationClaimsFromToken(registrationToken)
            then("토큰에서 추출한 registrationClaims가 반환되어야 한다."){
                result.shouldBeEqualUsingFields(registrationClaims)
            }
        }


    }

})