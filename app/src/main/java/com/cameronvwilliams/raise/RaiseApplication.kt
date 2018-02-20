package com.cameronvwilliams.raise

import com.cameronvwilliams.analytics.Analytics
import com.cameronvwilliams.raise.di.DaggerRaiseComponent
import com.cameronvwilliams.raise.di.RaiseComponent
import com.google.android.gms.ads.MobileAds
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

open class RaiseApplication : DaggerApplication() {

    private lateinit var raiseComponent: RaiseComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        raiseComponent = DaggerRaiseComponent.builder().application(this).build()
        return raiseComponent
    }

    override fun onCreate() {
        super.onCreate()

        MobileAds.initialize(this, BuildConfig.ADMOB_APP_ID)
        Analytics.initialize(this)
    }
}
