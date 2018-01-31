package com.cameronvwilliams.raise.ui.intro

import android.os.Bundle
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseActivity
import com.cameronvwilliams.raise.ui.Navigator
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_intro.*
import javax.inject.Inject

open class IntroActivity : BaseActivity() {

    override val currentTheme: Int = R.style.Intro_AppTheme

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        navigator.goToIntro(false)
        adView.loadAd(AdRequest.Builder().build())
    }
}
