package com.cameronvwilliams.raise.ui.poker

import android.content.Intent
import android.os.Bundle
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.BaseActivity
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.pending.PendingActivity
import com.cameronvwilliams.raise.ui.pending.PendingActivity.IntentOptions.getPokerGame
import com.cameronvwilliams.raise.ui.pending.PendingActivity.IntentOptions.getUserName
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.poker_activity.*
import javax.inject.Inject

class PokerActivity : BaseActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.poker_activity)

        with(PokerActivity.IntentOptions) {
            navigator.goToPoker(intent.getPokerGame())
        }
        adView.loadAd(AdRequest.Builder().build())
    }

    companion object IntentOptions {
        private const val EXTRA_POKER_GAME = "poker_game"

        fun Intent.getPokerGame(): PokerGame {
            return getParcelableExtra(EXTRA_POKER_GAME)
        }

        fun Intent.setPokerGame(game: PokerGame) {
            putExtra(EXTRA_POKER_GAME, game)
        }
    }
}
