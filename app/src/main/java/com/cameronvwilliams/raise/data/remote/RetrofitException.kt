package com.cameronvwilliams.raise.data.remote

import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class RetrofitException(
    override val message: String?, val url: String?, val response: Response<*>?, val kind: Kind,
    val exception: Throwable?, val retrofit: Retrofit?
) : RuntimeException() {

    enum class Kind {
        NETWORK,
        HTTP,
        UNEXPECTED
    }

    @Throws(IOException::class)
    fun <T> getErrorBodyAs(type: Class<T>): T? {
        if (response == null || this.response.errorBody() == null) {
            return null
        }
        val converter = retrofit?.responseBodyConverter<T>(type, arrayOfNulls<Annotation>(0))
        return converter?.convert(response.errorBody()!!)
    }

    companion object {
        @JvmStatic
        fun httpError(url: String, response: Response<*>, retrofit: Retrofit): RetrofitException {
            val message = response.code().toString() + " " + response.message()
            return RetrofitException(message, url, response, Kind.HTTP, null, retrofit)
        }

        @JvmStatic
        fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(exception.message, null, null, Kind.NETWORK, exception, null)
        }

        @JvmStatic
        fun unexpectedError(exception: Throwable): RetrofitException {
            return RetrofitException(
                exception.message,
                null,
                null,
                Kind.UNEXPECTED,
                exception,
                null
            )
        }
    }
}