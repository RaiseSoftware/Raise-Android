package com.cameronvwilliams.raise.data

import android.content.Context
import android.content.SharedPreferences
import com.cameronvwilliams.raise.di.ApplicationContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
abstract class DataModule {

    @Module
    companion object {

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
        fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
                context.getSharedPreferences("raise_preferences", Context.MODE_PRIVATE)
    }
}