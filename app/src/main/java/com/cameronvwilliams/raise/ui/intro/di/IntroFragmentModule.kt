package com.cameronvwilliams.raise.ui.intro.di

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.IntroContract
import com.cameronvwilliams.raise.ui.intro.presenters.CreatePresenter
import com.cameronvwilliams.raise.ui.intro.presenters.IntroPresenter
import com.cameronvwilliams.raise.ui.intro.presenters.JoinPresenter
import com.cameronvwilliams.raise.ui.intro.presenters.PasscodePresenter
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
abstract class IntroFragmentModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideIntroPresenter(navigator: Navigator): IntroContract.IntroUserActions =
            IntroPresenter(navigator)

        @Provides
        @JvmStatic
        fun provideCreatePresenter(navigator: Navigator, dm: DataManager):
                IntroContract.CreateUserActions = CreatePresenter(navigator, dm)

        @Provides
        @JvmStatic
        fun provideJoinPresenter(navigator: Navigator, dm: DataManager, gson: Gson):
                IntroContract.JoinUserActions = JoinPresenter(navigator, dm, gson)

        @Provides
        @JvmStatic
        fun providePasscodePresenter(navigator: Navigator, dm: DataManager):
                IntroContract.PasscodeUserActions = PasscodePresenter(navigator, dm)
    }
}