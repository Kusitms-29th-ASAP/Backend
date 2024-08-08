package com.asap.asapbackend.global.util

import com.asap.asapbackend.global.vo.Language
import org.springframework.stereotype.Component
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver

@Component
class LanguageExtractor {

    private val localeResolver: LocaleResolver = AcceptHeaderLocaleResolver().also {
        it.supportedLocales = Language.getSupportLanguages()
        it.setDefaultLocale(Language.getDefaultLanguage().locale)
    }


    fun getRequestLanguage(): Language {
        return Language.getLanguage(localeResolver.resolveLocale(HttpContextUtils.getHttpRequest()))
    }

}