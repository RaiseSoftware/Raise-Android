package com.cameronvwilliams.raise.ui.intro.di

import com.cameronvwilliams.raise.ui.intro.create.CreateCardFragment
import com.cameronvwilliams.raise.ui.intro.create.CreateFragment
import com.cameronvwilliams.raise.ui.intro.create.CreatePasscodeFragment
import com.cameronvwilliams.raise.ui.intro.views.*
import com.cameronvwilliams.raise.ui.offline.view.OfflineCardFragment
import com.cameronvwilliams.raise.ui.offline.view.OfflineFragment
import com.cameronvwilliams.raise.ui.offline.view.OfflineGameFragment
import com.cameronvwilliams.raise.ui.offline.view.OfflineSettingsFragment
import com.cameronvwilliams.raise.ui.pending.views.*
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
    abstract fun provideOfflineFragment(): OfflineFragment

    @ContributesAndroidInjector(modules = [IntroFragmentModule::class])
    abstract fun provideSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector(modules = [IntroFragmentModule::class])
    abstract fun provideAboutFragment(): AboutFragment

    @ContributesAndroidInjector(modules = [IntroFragmentModule::class])
    abstract fun provideHtmlFragment(): HtmlFragment

    @ContributesAndroidInjector(modules = [IntroFragmentModule::class])
    abstract fun provideFeedbackFragment(): FeedbackFragment

    @ContributesAndroidInjector(modules = [IntroFragmentModule::class])
    abstract fun provideCreateCardFragment(): CreateCardFragment

    @ContributesAndroidInjector(modules = [IntroFragmentModule::class])
    abstract fun provideCreatePasscodeFragment(): CreatePasscodeFragment

    @ContributesAndroidInjector(modules = [IntroFragmentModule::class])
    abstract fun provideJoinCardFragment(): JoinCardFragment

    @ContributesAndroidInjector(modules = [IntroFragmentModule::class])
    abstract fun provideOfflineCardFragment(): OfflineCardFragment

    @ContributesAndroidInjector(modules = [IntroFragmentModule::class])
    abstract fun provideOfflineGameFragment(): OfflineGameFragment

    @ContributesAndroidInjector(modules = [IntroFragmentModule::class])
    abstract fun provideOfflineSettingsFragment(): OfflineSettingsFragment

    @ContributesAndroidInjector()
    abstract fun providePendingFragment(): PendingFragment

    @ContributesAndroidInjector()
    abstract fun providePlayerListFragment(): PlayerListFragment

    @ContributesAndroidInjector()
    abstract fun providePokerInfoFragment(): PokerInfoFragment

    @ContributesAndroidInjector()
    abstract fun provideModeratorFragment(): ModeratorFragment

    @ContributesAndroidInjector()
    abstract fun provideCreateStoryFragment(): CreateStoryFragment
}
