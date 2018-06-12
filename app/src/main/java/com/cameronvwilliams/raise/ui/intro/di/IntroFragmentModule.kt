package com.cameronvwilliams.raise.ui.intro.di

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.presenters.CreatePresenter
import com.cameronvwilliams.raise.ui.intro.presenters.IntroPresenter
import com.cameronvwilliams.raise.ui.intro.presenters.JoinPresenter
import com.cameronvwilliams.raise.ui.intro.presenters.PasscodePresenter
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
    }
}