package com.cameronvwilliams.raise.ui.scanner.di

import com.cameronvwilliams.raise.ui.scanner.views.ScannerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScannerFragmentProvider {
    @ContributesAndroidInjector(modules = [ScannerFragmentModule::class])
    abstract fun provideScannerFragment(): ScannerFragment
}
