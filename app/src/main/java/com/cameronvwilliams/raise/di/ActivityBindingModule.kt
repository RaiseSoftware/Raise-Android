package com.cameronvwilliams.raise.di

import com.cameronvwilliams.raise.ui.intro.IntroActivity
import com.cameronvwilliams.raise.ui.intro.di.IntroFragmentProvider
import com.cameronvwilliams.raise.ui.intro.di.IntroModule
import com.cameronvwilliams.raise.ui.pending.PendingActivity
import com.cameronvwilliams.raise.ui.pending.di.PendingFragmentProvider
import com.cameronvwilliams.raise.ui.pending.di.PendingModule
import com.cameronvwilliams.raise.ui.poker.PokerActivity
import com.cameronvwilliams.raise.ui.poker.di.PokerFragmentProvider
import com.cameronvwilliams.raise.ui.poker.di.PokerModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [IntroModule::class, IntroFragmentProvider::class])
    internal abstract fun introActivity(): IntroActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [PendingModule::class, PendingFragmentProvider::class])
    internal abstract fun pendingActivity(): PendingActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [PokerModule::class, PokerFragmentProvider::class])
    internal abstract fun pokerActivity(): PokerActivity

}