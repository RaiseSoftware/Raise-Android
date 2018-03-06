package com.cameronvwilliams.raise.ui.poker.views


import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.di.ActivityContext
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.pending.views.PendingFragment
import com.cameronvwilliams.raise.ui.pending.views.PendingFragment.BundleOptions.getPokerGame
import com.cameronvwilliams.raise.ui.pending.views.PendingFragment.BundleOptions.getUserName
import com.cameronvwilliams.raise.ui.poker.PokerActivity
import com.cameronvwilliams.raise.ui.poker.views.adapter.PokerAdapter
import kotlinx.android.synthetic.main.poker_fragment.*
import javax.inject.Inject

class PokerFragment : BaseFragment() {

    @Inject
    lateinit var dm: DataManager

    @Inject
    lateinit var navigator: Navigator

    @field:[Inject ActivityContext]
    lateinit var activityContext: Context

    private lateinit var closeButtonDialog: AlertDialog

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        closeButtonDialog = AlertDialog.Builder(activity)
            .setTitle(getString(R.string.text_exit_game))
            .setMessage(getString(R.string.text_sure_exit))
            .setPositiveButton(android.R.string.yes, { dialog, _ ->
                dm.endGame(PokerGame("", DeckType.FIBONACCI, false))
                dialog.dismiss()
                activity.finish()
            })
            .setNegativeButton(android.R.string.no, { dialog, _ ->
                dialog.dismiss()
            })
            .create()

        return inflater!!.inflate(R.layout.poker_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener {
            closeButtonDialog.show()
        }

        val pokerGame: PokerGame = with(PokerGame) {
            arguments!!.getPokerGame()
        }

        val adapter =
            PokerAdapter((activityContext as PokerActivity).supportFragmentManager, pokerGame)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager, true)

//        dm.getGameEnd()
//            .subscribe { game ->
//                navigator.goToIntro(false)
//            }
    }

    companion object BundleOptions {
        private const val EXTRA_POKER_GAME = "poker_game"

        fun newInstance(): PokerFragment {
            return PokerFragment()
        }

        fun Bundle.getPokerGame(): PokerGame {
            return getParcelable(EXTRA_POKER_GAME)
        }

        fun Bundle.setPokerGame(game: PokerGame) {
            putParcelable(EXTRA_POKER_GAME, game)
        }
    }
}
