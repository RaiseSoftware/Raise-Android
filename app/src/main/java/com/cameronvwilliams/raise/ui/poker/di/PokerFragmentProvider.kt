package com.cameronvwilliams.raise.ui.poker.di

import com.cameronvwilliams.raise.ui.poker.views.PlayerCardFragment
import com.cameronvwilliams.raise.ui.poker.views.PokerCardFragment
import com.cameronvwilliams.raise.ui.poker.views.PokerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PokerFragmentProvider {
    @ContributesAndroidInjector(modules = [PokerFragmentModule::class])
    abstract fun providePokerFragment(): PokerFragment

    @ContributesAndroidInjector(modules = [PokerFragmentModule::class])
    abstract fun providePokerCardFragment(): PokerCardFragment

    @ContributesAndroidInjector(modules = [PokerFragmentModule::class])
    abstract fun providePlayerCardFragment(): PlayerCardFragment
}
