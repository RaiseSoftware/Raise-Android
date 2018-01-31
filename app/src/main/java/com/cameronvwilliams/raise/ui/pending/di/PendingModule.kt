package com.cameronvwilliams.raise.ui.pending.di

import android.content.Context
import android.support.v4.app.FragmentManager
import com.cameronvwilliams.raise.di.ActivityContext
import com.cameronvwilliams.raise.di.PerActivity
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.pending.PendingActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class PendingModule {
    @Binds
    @PerActivity
    @ActivityContext
    abstract fun bindContext(activity: PendingActivity): Context

    @Module
    companion object {

        @Provides
        @PerActivity
        @JvmStatic
        fun provideFragmentManager(activity: PendingActivity): FragmentManager =
            activity.supportFragmentManager

        @Provides
        @PerActivity
        @JvmStatic
        fun provideNavigator(fm: FragmentManager, @ActivityContext context: Context):
                Navigator = Navigator(fm, context)
    }
}
