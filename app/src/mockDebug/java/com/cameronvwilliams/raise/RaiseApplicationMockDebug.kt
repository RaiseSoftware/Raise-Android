package com.cameronvwilliams.raise

import com.cameronvwilliams.raise.di.DaggerRaiseComponentMockDebug
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class RaiseApplicationMockDebug: RaiseApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerRaiseComponentMockDebug.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        )

        Timber.plant(Timber.DebugTree())
    }
}
