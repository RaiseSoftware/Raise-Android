package com.cameronvwilliams.raise.data

import android.content.Context
import android.content.SharedPreferences
import com.cameronvwilliams.raise.BuildConfig
import com.cameronvwilliams.raise.data.remote.RaiseAPI
import com.cameronvwilliams.raise.data.remote.RxErrorHandlingCallAdapterFactory
import com.cameronvwilliams.raise.data.remote.SocketAPI
import com.cameronvwilliams.raise.data.remote.SocketClient
import com.cameronvwilliams.raise.di.ApplicationContext
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .build()

        @Provides
        @Singleton
        @JvmStatic
        fun provideRaiseApi(client: OkHttpClient, gson: Gson): RaiseAPI = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
            .build()
            .create(RaiseAPI::class.java)

        @Provides
        @Singleton
        @JvmStatic
        fun provideSocketAPI(gson: Gson, okHttpClient: OkHttpClient): SocketAPI =
            SocketClient(gson, okHttpClient, BuildConfig.SOCKET_URL)

        @Provides
        @Singleton
        @JvmStatic
        fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
            context.getSharedPreferences("raise_preferences", Context.MODE_PRIVATE)
    }
}