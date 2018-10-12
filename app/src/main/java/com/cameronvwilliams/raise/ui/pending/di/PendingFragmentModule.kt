package com.cameronvwilliams.raise.ui.pending.di

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.ui.pending.presenter.PlayerListPresenter
import dagger.Module
import dagger.Provides

@Module
abstract class PendingFragmentModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providePlayerListPresenter(dm: DataManager): PlayerListPresenter =
            PlayerListPresenter(dm)
    }
}
