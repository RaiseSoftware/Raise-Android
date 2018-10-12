package com.cameronvwilliams.raise.ui.poker.di

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.ui.poker.presenter.PokerCardPresenter
import dagger.Module
import dagger.Provides

@Module
abstract class PokerFragmentModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providePokerCardPresenter(dm: DataManager): PokerCardPresenter =
            PokerCardPresenter(dm)
    }
}