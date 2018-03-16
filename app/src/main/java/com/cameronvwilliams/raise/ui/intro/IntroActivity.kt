package com.cameronvwilliams.raise.ui.intro

import android.os.Bundle
import com.cameronvwilliams.raise.BuildConfig
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseActivity
import com.google.android.gms.ads.AdRequest
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import kotlinx.android.synthetic.main.intro_activity.*

open class IntroActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_activity)

        navigator.goToIntro(false)
        adView.loadAd(AdRequest.Builder().build())
        AppCenter.start(application, BuildConfig.APP_CENTER_KEY,
            Distribute::class.java, Analytics::class.java, Crashes::class.java)
    }

    }
}
