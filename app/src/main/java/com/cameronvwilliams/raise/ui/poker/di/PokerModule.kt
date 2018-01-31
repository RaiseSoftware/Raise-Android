package com.cameronvwilliams.raise.ui.poker.di

import android.content.Context
import android.support.v4.app.FragmentManager
import com.cameronvwilliams.raise.di.ActivityContext
import com.cameronvwilliams.raise.di.PerActivity
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.poker.PokerActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class PokerModule {
    @Binds
    @PerActivity
    @ActivityContext
    abstract fun bindContext(activity: PokerActivity): Context

    @Module
    companion object {

        @Provides
        @PerActivity
        @JvmStatic
        fun provideFragmentManager(activity: PokerActivity): FragmentManager =
            activity.supportFragmentManager

        @Provides
        @PerActivity
        @JvmStatic
        fun provideNavigator(fm: FragmentManager, @ActivityContext context: Context):
                Navigator = Navigator(fm, context)
    }
}