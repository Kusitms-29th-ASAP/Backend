package com.asap.asapbackend.global.util

import com.asap.asapbackend.global.vo.Language

interface TextTranslator {

    fun translate(text: String): TranslateResponse

    data class TranslateResponse(
        val translations: List<Translation>
    ){
        fun forEach(action: (Translation) -> Unit) {
            translations.forEach(action)
        }
    }

    data class Translation(
        val language: Language,
        val text: String
    )
}