package com.asap.asapbackend.domain.user.domain.model

import jakarta.persistence.Embeddable

@Embeddable
data class Agreement(
    val termsOfService: Boolean, // 서비스 이용약관
    val privacyPolicy: Boolean, // 개인정보 처리방침
    val marketing: Boolean // 마케팅 정보 수신 동의
){
    init{
        validate()
    }

    fun validate(){
        require(termsOfService) { "서비스 이용약관에 동의해주세요." }
        require(privacyPolicy) { "개인정보 처리방침에 동의해주세요." }
    }
}