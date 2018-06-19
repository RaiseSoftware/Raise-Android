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
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.pending_fragment.*
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
        private const val EXTRA_MODERATOR_MODE = "moderator_mode"

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

        fun Bundle.getModeratorMode(): Boolean {
            return getBoolean(EXTRA_MODERATOR_MODE)
        }

        fun Bundle.setModeratorMode(value: Boolean) {
            putBoolean(EXTRA_MODERATOR_MODE, value)
        }
    }

    private lateinit var closeButtonDialog: AlertDialog
    private lateinit var pokerGame: PokerGame
    private lateinit var userName: String
    private var moderatorMode: Boolean = false
    private val subscriptions: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pending_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(BundleOptions) {
            pokerGame = arguments!!.getPokerGame()
            userName = arguments!!.getUserName()
            moderatorMode = arguments!!.getModeratorMode()
        }

        val adapter = PendingAdapter(
            (activityContext as PendingActivity).supportFragmentManager,
            pokerGame, moderatorMode
        )
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager, true)

        createCloseButtonDialog()

        if (moderatorMode) {
            startButtonBackground.visibility = View.VISIBLE
            startButton.setOnClickListener {
                dm.startGame()
            }
        } else {
            startButtonBackground.visibility = View.GONE
        }


        closeButton.setOnClickListener {
            closeButtonDialog.show()
        }

        dm.joinGame()

        subscriptions.add(dm.getPlayersInGame()
            .subscribe { result ->
                startButton.isEnabled = result.first!!.isNotEmpty()
            })

        subscriptions.add(dm.getGameStart()
            .subscribe {
                navigator.goToPokerGameView(pokerGame)
                activity!!.finish()
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscriptions.clear()
    }

    override fun onBackPressed(): Boolean {
        closeButtonDialog.show()

        return true
    }

    private fun createCloseButtonDialog() {
        when (moderatorMode) {
            true -> {
                closeButtonDialog = AlertDialog.Builder(activityContext)
                    .setTitle(getString(R.string.text_end_game))
                    .setMessage(getString(R.string.text_sure_end))
                    .setPositiveButton(android.R.string.yes) { dialog, _ ->
                        dm.endGame()
                    }
                    .setNegativeButton(android.R.string.no) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
            }
            false -> {
                closeButtonDialog = AlertDialog.Builder(activityContext)
                    .setTitle(getString(R.string.text_exit_game))
                    .setMessage(getString(R.string.text_sure_exit))
                    .setPositiveButton(android.R.string.yes) { dialog, _ ->
                        dm.leaveGame()
                        dialog.dismiss()
                        activity!!.finish()
                    }
                    .setNegativeButton(android.R.string.no) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
            }
        }
    }
}
