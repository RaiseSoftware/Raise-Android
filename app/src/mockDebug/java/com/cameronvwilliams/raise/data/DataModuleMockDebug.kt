package com.cameronvwilliams.raise.data

import android.content.Context
import android.content.SharedPreferences
import com.cameronvwilliams.raise.BuildConfig
import com.cameronvwilliams.raise.data.remote.*
import com.cameronvwilliams.raise.di.ApplicationContext
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModuleMockDebug {
    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic fun provideRaiseApi(retrofit: Retrofit): RaiseAPI = RaiseAPIMock(retrofit)

        @Provides
        @Singleton
        @JvmStatic fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
            .build()

        @Provides
        @Singleton
        @JvmStatic
        fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(AcceptLanguageHeaderInterceptor())
            .build()

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
