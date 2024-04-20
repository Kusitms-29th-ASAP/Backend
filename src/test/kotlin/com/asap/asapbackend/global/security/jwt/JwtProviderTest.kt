package com.asap.asapbackend.global.security.jwt

import com.asap.asapbackend.fixture.generateFixture
import com.asap.asapbackend.generateBasicParser
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class JwtProviderTest : BehaviorSpec({
    val jwtProperties = JwtProperties(
        secret = "testSecrettestSecrettestSecrettestSecrettestSecret",
        accessTokenExpirationTime = 10000L,
        refreshTokenExpirationTime = 10000L
    )
    val jwtKeyFactory = JwtKeyFactory(jwtProperties)
    val jwtValidator = mockk<JwtValidator>(relaxed = true)

    val jwtProvider = JwtProvider(jwtProperties, jwtKeyFactory, jwtValidator)

    given("토큰 생성 과정에서") {
        val userClaims: PrivateClaims.UserClaims = generateFixture()
        `when`("generateAccessToken를 호출하면 access token이 생성되고") {
            val result = jwtProvider.generateAccessToken(userClaims)
            val payload = generateBasicParser(
                result,
                jwtKeyFactory.generateKey(),
                PrivateClaims.retrieveClaimsClassType()
            ).parseSignedClaims(result).payload
            then("isser가 ${JwtConst.TOKEN_ISSUER} 여야 한다.") {
                payload.issuer shouldBe JwtConst.TOKEN_ISSUER
            }
            then("tokenType이 ${TokenType.ACCESS_TOKEN} 여야 한다.") {
                payload.get(JwtConst.TOKEN_TYPE, TokenType::class.java) shouldBe TokenType.ACCESS_TOKEN
            }
            then("userClaims가 토큰 생성시 사용한 userClaims여야 한다.") {
                payload.get(JwtConst.USER_CLAIMS, PrivateClaims.UserClaims::class.java)
                    .shouldBeEqualUsingFields(userClaims)
            }
        }

        `when`("generateRefreshToken을 호출하면 refresh token이 생성되고") {
            val result = jwtProvider.generateRefreshToken(userClaims)
            val payload = generateBasicParser(
                result,
                jwtKeyFactory.generateKey(),
                PrivateClaims.retrieveClaimsClassType()
            ).parseSignedClaims(result).payload
            then("isser가 ${JwtConst.TOKEN_ISSUER} 여야 한다.") {
                payload.issuer shouldBe JwtConst.TOKEN_ISSUER
            }
            then("tokenType이 ${TokenType.REFRESH_TOKEN} 여야 한다.") {
                payload.get(JwtConst.TOKEN_TYPE, TokenType::class.java) shouldBe TokenType.REFRESH_TOKEN
            }
            then("userClaims가 토큰 생성시 사용한 userClaims여야 한다.") {
                payload.get(JwtConst.USER_CLAIMS, PrivateClaims.UserClaims::class.java)
                    .shouldBeEqualUsingFields(userClaims)
            }
        }
    }

    given("토큰 재발급 과정에서") {
        val refreshToken: String = generateFixture()
        val userClaims: PrivateClaims.UserClaims = generateFixture()
        every { jwtProvider.extractUserClaimsFromToken(refreshToken, TokenType.REFRESH_TOKEN) } returns userClaims
        `when`("reissueToken을 호출하면") {
            val result = jwtProvider.reissueToken(refreshToken)
            then("accessToken과 refreshToken이 생성되어야 한다.") {
                val generatedAccessToken = result.accessToken
                val generatedRefreshToken = result.refreshToken
                val accessTokenPayload = generateBasicParser(
                    generatedAccessToken,
                    jwtKeyFactory.generateKey(),
                    PrivateClaims.retrieveClaimsClassType()
                ).parseSignedClaims(generatedAccessToken).payload
                val refreshTokenPayload = generateBasicParser(
                    generatedRefreshToken,
                    jwtKeyFactory.generateKey(),
                    PrivateClaims.retrieveClaimsClassType()
                ).parseSignedClaims(generatedRefreshToken).payload
                accessTokenPayload.issuer shouldBe JwtConst.TOKEN_ISSUER
                accessTokenPayload.get(JwtConst.TOKEN_TYPE, TokenType::class.java) shouldBe TokenType.ACCESS_TOKEN
                accessTokenPayload.get(JwtConst.USER_CLAIMS, PrivateClaims.UserClaims::class.java)
                    .shouldBeEqualUsingFields(userClaims)
                refreshTokenPayload.issuer shouldBe JwtConst.TOKEN_ISSUER
                refreshTokenPayload.get(JwtConst.TOKEN_TYPE, TokenType::class.java) shouldBe TokenType.REFRESH_TOKEN
                refreshTokenPayload.get(JwtConst.USER_CLAIMS, PrivateClaims.UserClaims::class.java)
                    .shouldBeEqualUsingFields(userClaims)
            }
        }
    }

    given("토큰 추출 과정에서") {
        val userClaims: PrivateClaims.UserClaims = generateFixture()
        val token: String = jwtProvider.generateAccessToken(userClaims)
        every { jwtValidator.initializeJwtParser(TokenType.ACCESS_TOKEN) } returns generateBasicParser(
            token,
            jwtKeyFactory.generateKey(),
            PrivateClaims.retrieveClaimsClassType()
        )
        `when`("extractUserClaimsFromToken을 호출하면") {
            val result = jwtProvider.extractUserClaimsFromToken(token, TokenType.ACCESS_TOKEN)
            then("토큰에서 추출한 userClaims가 반환되어야 한다.") {
                result.shouldBeEqualUsingFields(userClaims)
            }
        }
    }
})