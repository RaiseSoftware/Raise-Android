package com.cameronvwilliams.raise.data

import com.cameronvwilliams.raise.BuildConfig
import com.cameronvwilliams.raise.data.remote.RaiseAPI
import com.cameronvwilliams.raise.data.remote.RxErrorHandlingCallAdapterFactory
import com.cameronvwilliams.raise.data.remote.SocketClient
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
abstract class DataModuleDebug {

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()

        @Provides
        @Singleton
        @JvmStatic fun provideRaiseApi(client: OkHttpClient, gson: Gson): RaiseAPI = Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build()
                .create(RaiseAPI::class.java)

        @Provides
        @Singleton
        @JvmStatic fun provideSocketClient(gson: Gson, okHttpClient: OkHttpClient): SocketClient
                = SocketClient(gson, okHttpClient, BuildConfig.SOCKET_URL)
    }
}
