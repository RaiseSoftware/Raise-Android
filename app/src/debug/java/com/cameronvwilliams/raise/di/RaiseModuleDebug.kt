package com.cameronvwilliams.raise.di

import android.content.Context
import com.cameronvwilliams.raise.RaiseApplicationDebug
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RaiseModuleDebug {

    @Binds
    @ApplicationContext
    abstract fun bindContext(application: RaiseApplicationDebug): Context

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideGson(): Gson = Gson()
    }
}
