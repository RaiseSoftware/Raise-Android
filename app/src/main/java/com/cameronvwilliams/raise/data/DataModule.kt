package com.cameronvwilliams.raise.data

import android.content.Context
import android.content.SharedPreferences
import com.cameronvwilliams.raise.BuildConfig
import com.cameronvwilliams.raise.data.remote.*
import com.cameronvwilliams.raise.di.ApplicationContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.google.firebase.firestore.FirebaseFirestoreSettings



@Module
abstract class DataModule {

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(AcceptLanguageHeaderInterceptor())
                .build()

        @Provides
        @Singleton
        @JvmStatic
        fun provideFirebaseDatabase(): FirebaseFirestore {
            val firestore = FirebaseFirestore.getInstance()
            val settings = FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build()
            firestore.firestoreSettings = settings
            return firestore
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideFirebaseAuth() = FirebaseAuth.getInstance()

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