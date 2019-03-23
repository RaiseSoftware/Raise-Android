package com.cameronvwilliams.raise.ui.splash

import android.content.Context
import com.cameronvwilliams.raise.di.ActivityContext
import com.cameronvwilliams.raise.di.PerActivity
import dagger.Binds
import dagger.Module

@Module
abstract class SplashModule {

    @Binds
    @PerActivity
    @ActivityContext
    abstract fun bindContext(activity: SplashActivity): Context
}
