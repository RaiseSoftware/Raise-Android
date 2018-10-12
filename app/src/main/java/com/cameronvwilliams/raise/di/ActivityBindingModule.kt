package com.cameronvwilliams.raise.di

import com.cameronvwilliams.raise.ui.intro.IntroActivity
import com.cameronvwilliams.raise.ui.intro.di.IntroFragmentProvider
import com.cameronvwilliams.raise.ui.intro.di.IntroModule
import com.cameronvwilliams.raise.ui.offline.OfflineActivity
import com.cameronvwilliams.raise.ui.offline.di.OfflineFragmentProvider
import com.cameronvwilliams.raise.ui.offline.di.OfflineModule
import com.cameronvwilliams.raise.ui.pending.PendingActivity
import com.cameronvwilliams.raise.ui.pending.di.PendingFragmentProvider
import com.cameronvwilliams.raise.ui.pending.di.PendingModule
import com.cameronvwilliams.raise.ui.poker.PokerActivity
import com.cameronvwilliams.raise.ui.poker.di.PokerFragmentProvider
import com.cameronvwilliams.raise.ui.poker.di.PokerModule
import com.cameronvwilliams.raise.ui.scanner.ScannerActivity
import com.cameronvwilliams.raise.ui.scanner.di.ScannerFragmentProvider
import com.cameronvwilliams.raise.ui.scanner.di.ScannerModule
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

    @PerActivity
    @ContributesAndroidInjector(modules = [ScannerModule::class, ScannerFragmentProvider::class])
    internal abstract fun scannerActivity(): ScannerActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [OfflineModule::class, OfflineFragmentProvider::class])
    internal abstract fun offlineActivity(): OfflineActivity
}