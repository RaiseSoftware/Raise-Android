package com.cameronvwilliams.raise.ui.intro

import android.os.Bundle
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseActivity
import com.cameronvwilliams.raise.ui.Navigator
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.intro_activity.*
import net.hockeyapp.android.UpdateManager
import javax.inject.Inject

class IntroActivityDebug: BaseActivity() {

    @Inject lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_activity)

        adView.loadAd(AdRequest.Builder().build())

        navigator.goToIntro(false)
        UpdateManager.register(this)
    }

    override fun onPause() {
        super.onPause()
        UpdateManager.unregister()
    }
}
