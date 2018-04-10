package com.cameronvwilliams.raise.data

import android.content.Context
import android.content.SharedPreferences
import com.cameronvwilliams.raise.data.remote.*
import com.cameronvwilliams.raise.di.ApplicationContext
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class DataModuleMockDebug {
    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic fun provideRaiseApi(): RaiseAPI = RaiseAPIMock()

        @Provides
        @Singleton
        @JvmStatic fun provideSocketClient(): SocketAPI
                = SocketClientMock()

        @Provides
        @Singleton
        @JvmStatic
        fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
            context.getSharedPreferences("raise_preferences_debug", Context.MODE_PRIVATE)
    }
}
