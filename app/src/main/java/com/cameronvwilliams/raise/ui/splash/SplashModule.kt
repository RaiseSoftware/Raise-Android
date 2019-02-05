package com.cameronvwilliams.raise.ui.splash

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
abstract class SplashModule {

    @Binds
    @PerActivity
    @ActivityContext
    abstract fun bindContext(activity: SplashActivity): Context
}
