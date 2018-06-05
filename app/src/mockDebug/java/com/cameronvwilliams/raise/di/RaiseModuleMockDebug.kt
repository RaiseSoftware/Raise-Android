package com.cameronvwilliams.raise.di

import android.content.Context
import com.cameronvwilliams.raise.RaiseApplicationDebug
import com.cameronvwilliams.raise.RaiseApplicationMockDebug
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RaiseModuleMockDebug {

    @Binds
    @ApplicationContext
    abstract fun bindContext(application: RaiseApplicationMockDebug): Context

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideGson(): Gson = Gson()
    }
}
