package com.cameronvwilliams.raise.ui.pending.di

import com.cameronvwilliams.raise.ui.pending.views.PendingFragment
import com.cameronvwilliams.raise.ui.pending.views.PlayerListFragment
import com.cameronvwilliams.raise.ui.pending.views.PokerInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PendingFragmentProvider {
    @ContributesAndroidInjector(modules = [PendingFragmentModule::class])
    abstract fun providePlayerListFragment(): PlayerListFragment

    @ContributesAndroidInjector(modules = [PendingFragmentModule::class])
    abstract fun providePokerInfoFragment(): PokerInfoFragment

    @ContributesAndroidInjector(modules = [PendingFragmentModule::class])
    abstract fun providePendingFragment(): PendingFragment
}
