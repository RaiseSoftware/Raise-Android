package com.cameronvwilliams.raise.ui.poker

import android.content.Intent
import android.os.Bundle
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.Navigator
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class PokerActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.poker_activity)

        with(PokerActivity.IntentOptions) {
            navigator.goToPoker(intent.getPokerGame())
        }
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
