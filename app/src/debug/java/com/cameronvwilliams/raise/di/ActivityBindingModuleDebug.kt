package com.cameronvwilliams.raise.di

import com.cameronvwilliams.raise.ui.intro.IntroActivityDebug
import com.cameronvwilliams.raise.ui.intro.IntroModuleDebug
import com.cameronvwilliams.raise.ui.intro.di.IntroFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModuleDebug : ActivityBindingModule() {
    @PerActivity
    @ContributesAndroidInjector(modules = [IntroModuleDebug::class, IntroFragmentProvider::class])
    internal abstract fun introActivityDebug(): IntroActivityDebug
}