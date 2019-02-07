package com.cameronvwilliams.raise.ui.pending.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.data.model.Role
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.MainActivity
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.pending.views.adapter.PendingAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.pending_fragment.*
import javax.inject.Inject

class PendingFragment : BaseFragment() {

    @Inject
    lateinit var dm: DataManager

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var activityContext: MainActivity

    companion object BundleOptions {
        private const val EXTRA_POKER_GAME = "poker_game"
        private const val EXTRA_PLAYER = "player"

        fun newInstance(pokerGame: PokerGame, player: Player): PendingFragment {
            val fragment = PendingFragment()

            val bundle = Bundle()

            bundle.putParcelable(EXTRA_POKER_GAME, pokerGame)
            bundle.putParcelable(EXTRA_PLAYER, player)

            fragment.arguments = bundle

            return fragment
        }
    }

    private lateinit var closeButtonDialog: AlertDialog
    private lateinit var pokerGame: PokerGame
    private lateinit var player: Player
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

        pokerGame = arguments!!.getParcelable(EXTRA_POKER_GAME)!!
        player = arguments!!.getParcelable(EXTRA_PLAYER)!!
        moderatorMode = player.roles.contains(Role.MODERATOR)

        val adapter = PendingAdapter(
            activityContext.supportFragmentManager,
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
    }

    override fun onResume() {
        super.onResume()
        dm.joinGame(pokerGame.uid!!, player)
            .subscribe()

        subscriptions.add(dm.getPlayersInGame(pokerGame.uid!!)
            .subscribe { result ->
                startButton.isEnabled = result.first!!.isNotEmpty()
            })

//        subscriptions.add(dm.getGameStart()
//            .subscribe {
//                navigator.goToPokerGameView(pokerGame)
//                activity?.finish()
//            })
//
//        subscriptions.add(dm.getGameEnd()
//            .subscribe {
//                activity?.finish()
//            })
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
                        dm.leaveGame(pokerGame.uid!!, player)
                        dialog.dismiss()
                        navigator.goBackToIntro()
                    }
                    .setNegativeButton(android.R.string.no) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
            }
        }
    }
}
