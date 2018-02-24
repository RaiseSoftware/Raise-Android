package com.cameronvwilliams.raise.ui.scanner.di

import android.content.Context
import android.support.v4.app.FragmentManager
import com.cameronvwilliams.raise.di.ActivityContext
import com.cameronvwilliams.raise.di.PerActivity
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.scanner.ScannerActivity
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
abstract class ScannerModule {

    @Binds
    @PerActivity
    @ActivityContext
    abstract fun bindContext(activity: ScannerActivity): Context

    @Module
    companion object {
        @Provides
        @PerActivity
        @JvmStatic
        fun provideFragmentManager(activity: ScannerActivity): FragmentManager =
            activity.supportFragmentManager

        @Provides
        @PerActivity
        @JvmStatic
        fun provideNavigator(fm: FragmentManager, @ActivityContext context: Context):
                Navigator = Navigator(fm, context)
    }
}

