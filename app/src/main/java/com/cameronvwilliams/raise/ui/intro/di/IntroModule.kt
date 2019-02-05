package com.cameronvwilliams.raise.ui.intro.di

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.cameronvwilliams.raise.di.ActivityContext
import com.cameronvwilliams.raise.di.PerActivity
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.MainActivity
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class IntroModule {

    @Binds
    @PerActivity
    @ActivityContext
    abstract fun bindContext(activity: MainActivity): Context

    @Module
    companion object {
        @Provides
        @PerActivity
        @JvmStatic
        fun provideFragmentManager(activity: MainActivity): FragmentManager =
            activity.supportFragmentManager

        @Provides
        @PerActivity
        @JvmStatic
        fun provideNavigator(fm: FragmentManager, @ActivityContext context: Context):
                Navigator = Navigator(fm, context)
    }
}
