package com.cameronvwilliams.raise.di

import android.content.Context
import com.cameronvwilliams.raise.RaiseApplication
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RaiseModule {

    @Binds
    @ApplicationContext
    abstract fun bindContext(application: RaiseApplication): Context

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideGson(): Gson = Gson()
    }
}
