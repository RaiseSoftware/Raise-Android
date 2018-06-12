package com.cameronvwilliams.raise.ui.scanner.di

import com.cameronvwilliams.raise.ui.scanner.ScannerPresenter
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
abstract class ScannerFragmentModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideScannerPresenter(gson: Gson): ScannerPresenter =
            ScannerPresenter(gson)

    }
}