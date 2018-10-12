package com.cameronvwilliams.raise.ui.offline.di

import com.cameronvwilliams.raise.ui.offline.view.OfflineGameFragment
import com.cameronvwilliams.raise.ui.offline.view.OfflineSettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class OfflineFragmentProvider {
    @ContributesAndroidInjector(modules = [OfflineFragmentModule::class])
    abstract fun provideOfflineCardFragment(): OfflineGameFragment

    @ContributesAndroidInjector(modules = [OfflineFragmentModule::class])
    abstract fun provideOfflineSettingsFragment(): OfflineSettingsFragment
}
