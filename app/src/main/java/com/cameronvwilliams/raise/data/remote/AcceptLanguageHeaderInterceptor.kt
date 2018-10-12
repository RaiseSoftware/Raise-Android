package com.cameronvwilliams.raise.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class AcceptLanguageHeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithHeaders = originalRequest.newBuilder()
                .addHeader("Accept-Language", localeToBcp47Language(Locale.getDefault()))
                .build()

        return chain.proceed(requestWithHeaders)
    }

    private fun localeToBcp47Language(loc: Locale): String {
        val separator = '-'
        var language = loc.language
        var region = loc.country
        var variant = loc.variant
        
        if (language == "no" && region == "NO" && variant == "NY") {
            language = "nn"
            region = "NO"
            variant = ""
        }

        if (language.isEmpty() || !language.matches("\\p{Alpha}{2,8}".toRegex())) {
            language = "und"
        } else if (language == "iw") {
            language = "he"        
        } else if (language == "in") {
            language = "id"        
        } else if (language == "ji") {
            language = "yi"        
        }

        if (!region.matches("\\p{Alpha}{2}|\\p{Digit}{3}".toRegex())) {
            region = ""
        }

        if (!variant.matches("\\p{Alnum}{5,8}|\\p{Digit}\\p{Alnum}{3}".toRegex())) {
            variant = ""
        }

        val bcp47Tag = StringBuilder(language)
        if (!region.isEmpty()) {
            bcp47Tag.append(separator).append(region)
        }
        if (!variant.isEmpty()) {
            bcp47Tag.append(separator).append(variant)
        }

        return bcp47Tag.toString()
    }
}