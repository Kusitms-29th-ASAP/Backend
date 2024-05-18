package com.asap.asapbackend.global.util

interface TextSummaryHelper {
    @Throws(UtilException.TextSummarizationException::class)
    fun summarizeText(text: String): List<String>
}