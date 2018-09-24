package com.cameronvwilliams.raise.ui.pending

import android.content.Intent
import android.os.Bundle
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.BaseActivity
import kotlinx.android.synthetic.main.pending_activity.*

class PendingActivity : BaseActivity() {

    companion object IntentOptions {
        private const val EXTRA_POKER_GAME = "poker_game"
        private const val EXTRA_USER_NAME = "user_name"
        private const val EXTRA_MODERATOR_MODE = "moderator_mode"

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

        fun Intent.getModeratorMode(): Boolean {
            return getBooleanExtra(EXTRA_MODERATOR_MODE, false)
        }

        fun Intent.setModeratorMode(value: Boolean) {
            putExtra(EXTRA_MODERATOR_MODE, value)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pending_activity)
        with(PendingActivity.IntentOptions) {
            navigator.goToPending(intent.getPokerGame(), intent.getUserName(), intent.getModeratorMode())
        }
    }
}
