package com.cameronvwilliams.raise.ui.pending.views


import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.di.ActivityContext
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.pending.PendingActivity
import com.cameronvwilliams.raise.ui.pending.views.adapter.PendingAdapter
import kotlinx.android.synthetic.main.fragment_pending.*
import javax.inject.Inject

class PendingFragment : BaseFragment() {

    @Inject
    lateinit var dm: DataManager

    @Inject
    lateinit var navigator: Navigator

    @field:[Inject ActivityContext]
    lateinit var activityContext: Context

    companion object BundleOptions {
        private const val EXTRA_POKER_GAME = "poker_game"
        private const val EXTRA_USER_NAME = "user_name"

        fun newInstance(): PendingFragment {
            return PendingFragment()
        }

        fun Bundle.getPokerGame(): PokerGame {
            return getParcelable(EXTRA_POKER_GAME)
        }

        fun Bundle.setPokerGame(game: PokerGame) {
            putParcelable(EXTRA_POKER_GAME, game)
        }

        fun Bundle.getUserName(): String {
            return getString(EXTRA_USER_NAME)
        }

        fun Bundle.setUserName(name: String) {
            putString(EXTRA_USER_NAME, name)
        }
    }

    private lateinit var backButtonDialog: AlertDialog
    private lateinit var pokerGame: PokerGame
    private lateinit var userName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(BundleOptions) {
            pokerGame = arguments!!.getPokerGame()
            userName = arguments!!.getUserName()
        }

        val adapter = PendingAdapter(
            (activityContext as PendingActivity).supportFragmentManager,
            pokerGame
        )
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager, true)

        backButtonDialog = AlertDialog.Builder(activityContext)
            .setTitle(getString(R.string.text_exit_game))
            .setMessage(getString(R.string.text_sure_exit))
            .setPositiveButton(android.R.string.yes, { dialog, _ ->
                dm.leaveGame()
                dialog.dismiss()
                onBackPressed()
            })
            .setNegativeButton(android.R.string.no, { dialog, _ ->
                dialog.dismiss()
            })
            .create()

        startButton.setOnClickListener {
            dm.startGame(pokerGame)
        }

        backButton.setOnClickListener {
            backButtonDialog.show()
        }

        if (pokerGame.requiresPasscode) {
            dm.joinGame(pokerGame.gameId, userName, pokerGame.passcode)
        } else {
            dm.joinGame(pokerGame.gameId, userName)
        }

        dm.getPlayersInGame()
            .subscribe { result ->
                startButton.isEnabled = result.first!!.isNotEmpty()
            }

        dm.getGameStart()
            .subscribe { game ->
                navigator.goToPokerGameView()
            }
    }
}
