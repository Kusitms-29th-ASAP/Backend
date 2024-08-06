package com.asap.asapbackend.global.vo

import java.util.*

enum class Language(
    val locale: Locale
) {
    KOREAN(Locale.KOREAN),
    ENGLISH(Locale.ENGLISH),
    CHINESE(Locale.CHINESE),
    JAPANESE(Locale.JAPANESE),
    VIETNAMESE(Locale("vi")),
    PILIPINO(Locale("pi"));

    companion object {
        fun getSupportLanguages(): List<Locale> {
            return entries.map(Language::locale).toList()
        }

        fun getDefaultLanguage(): Language {
            return KOREAN
        }

        fun getLanguage(locale: Locale): Language {
            return entries.find { it.locale.equals(locale) } ?: throw IllegalArgumentException("지원하지 않는 언어입니다.")
        }

        fun getAllLanguageNames(): List<String> {
            return entries.map { it.name }
        }
    }

}