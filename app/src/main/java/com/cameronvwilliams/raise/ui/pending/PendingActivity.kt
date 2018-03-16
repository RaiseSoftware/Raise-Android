package com.cameronvwilliams.raise.ui.pending

import android.content.Intent
import android.os.Bundle
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.BaseActivity
import com.cameronvwilliams.raise.ui.Navigator
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.pending_activity.*
import javax.inject.Inject


class PendingActivity : BaseActivity() {

    companion object IntentOptions {
        private const val EXTRA_POKER_GAME = "poker_game"
        private const val EXTRA_USER_NAME = "user_name"

        fun Intent.getPokerGame(): PokerGame {
            return getParcelableExtra(EXTRA_POKER_GAME)
        }

        fun Intent.setPokerGame(game: PokerGame) {
            putExtra(EXTRA_POKER_GAME, game)
        }

        fun Intent.getUserName(): String {
            return getStringExtra(EXTRA_USER_NAME)
        }

        fun Intent.setUserName(name: String) {
            putExtra(EXTRA_USER_NAME, name)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pending_activity)
        with(PendingActivity.IntentOptions) {
            navigator.goToPending(intent.getPokerGame(), intent.getUserName())
        }
        adView.loadAd(AdRequest.Builder().build())
    }
}
