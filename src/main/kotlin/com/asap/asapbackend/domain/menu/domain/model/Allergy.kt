package com.asap.asapbackend.domain.menu.domain.model

enum class Allergy(
    val number: Int,
    val description: String
) {
    EGG(1, "난류"),
    MILK(2, "우유"),
    BUCKWHEAT(3, "메밀"),
    PEANUT(4, "땅콩"),
    SOYBEAN(5, "대두"),
    WHEAT(6, "밀"),
    MACKEREL(7, "고등어"),
    CRAB(8, "게"),
    SHRIMP(9, "새우"),
    PORK(10, "돼지고기"),
    PEACH(11, "복숭아"),
    TOMATO(12, "토마토"),
    SULFITES(13, "아황산류"),
    WALNUT(14, "호두"),
    CHICKEN(15, "닭고기"),
    BEEF(16, "쇠고기"),
    SQUID(17, "오징어"),
    SHELLFISH(18, "조개류(굴, 전복, 홍합 포함)"),
    PINE_NUT(19, "잣")
}