package com.cameronvwilliams.raise.ui.poker

import android.os.Bundle
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseActivity
import com.cameronvwilliams.raise.ui.Navigator
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.poker_activity.*
import javax.inject.Inject

class PokerActivity : BaseActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.poker_activity)
        navigator.goToPoker()
        adView.loadAd(AdRequest.Builder().build())
    }
}
