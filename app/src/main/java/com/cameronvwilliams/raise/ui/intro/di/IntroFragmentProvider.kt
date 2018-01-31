package com.cameronvwilliams.raise.ui.intro.di

import com.cameronvwilliams.raise.ui.intro.views.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class IntroFragmentProvider {
    @ContributesAndroidInjector(modules = [IntroFragmentModule::class])
    abstract fun provideIntroFragment(): IntroFragment

    @ContributesAndroidInjector(modules = [IntroFragmentModule::class])
    abstract fun provideCreateGameFragment(): CreateFragment

    @ContributesAndroidInjector(modules = [IntroFragmentModule::class])
    abstract fun provideJoinGameFragment(): JoinFragment

    @ContributesAndroidInjector(modules = [IntroFragmentModule::class])
    abstract fun providePasscodeFragment(): PasscodeFragment

    @ContributesAndroidInjector(modules = [IntroFragmentModule::class])
    abstract fun provideSettingsFragment(): SettingsFragment
}
