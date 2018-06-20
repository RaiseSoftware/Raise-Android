package com.cameronvwilliams.raise.ui.intro.di

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.presenters.*
import dagger.Module
import dagger.Provides

@Module
abstract class IntroFragmentModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideIntroPresenter(navigator: Navigator): IntroPresenter =
            IntroPresenter(navigator)

        @Provides
        @JvmStatic
        fun provideCreatePresenter(navigator: Navigator, dm: DataManager): CreatePresenter =
            CreatePresenter(navigator, dm)

        @Provides
        @JvmStatic
        fun provideJoinPresenter(navigator: Navigator, dm: DataManager): JoinPresenter =
            JoinPresenter(navigator, dm)

        @Provides
        @JvmStatic
        fun providePasscodePresenter(navigator: Navigator, dm: DataManager): PasscodePresenter =
            PasscodePresenter(navigator, dm)

        @Provides
        @JvmStatic
        fun provideSettingsPresenter(navigator: Navigator): SettingsPresenter =
            SettingsPresenter(navigator)

        @Provides
        @JvmStatic
        fun provideAboutPresenter(navigator: Navigator): AboutPresenter =
            AboutPresenter(navigator)

        @Provides
        @JvmStatic
        fun provideFeedbackPresenter(navigator: Navigator): FeedbackPresenter =
            FeedbackPresenter(navigator)

        @Provides
        @JvmStatic
        fun provideHtmlPresenter(navigator: Navigator): HtmlPresenter =
            HtmlPresenter(navigator)
    }
}