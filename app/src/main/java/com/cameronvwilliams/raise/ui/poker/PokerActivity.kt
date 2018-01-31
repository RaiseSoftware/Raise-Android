package com.cameronvwilliams.raise.ui.poker

import android.os.Bundle
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseActivity
import com.cameronvwilliams.raise.ui.Navigator
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_poker.*
import javax.inject.Inject

class PokerActivity : BaseActivity() {

    override val currentTheme: Int = R.style.Poker_AppTheme

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poker)
        navigator.goToPoker()
        adView.loadAd(AdRequest.Builder().build())
    }
}
