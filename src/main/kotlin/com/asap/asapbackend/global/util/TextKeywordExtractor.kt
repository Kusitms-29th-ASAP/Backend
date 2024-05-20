package com.asap.asapbackend.global.util

interface TextKeywordExtractor {
    @Throws(UtilException.TextKeywordExtractionException::class)
    fun extractKeywords(text: String): List<String>
}