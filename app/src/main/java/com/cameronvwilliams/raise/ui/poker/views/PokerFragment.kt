package com.cameronvwilliams.raise.ui.poker.views


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.data.model.Story
import com.cameronvwilliams.raise.di.ActivityContext
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.Navigator
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
    private lateinit var storyDialog: AlertDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        closeButtonDialog = AlertDialog.Builder(activityContext)
            .setTitle(getString(R.string.text_exit_game))
            .setMessage(getString(R.string.text_sure_exit))
            .setPositiveButton(android.R.string.yes) { dialog, _ ->
                dm.endGame()
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        return inflater.inflate(R.layout.poker_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener {
            closeButtonDialog.show()
        }

        val pokerGame: PokerGame = with(PokerGame) {
            arguments!!.getPokerGame()
        }

        val adapter = PokerAdapter((activityContext as PokerActivity).supportFragmentManager, pokerGame)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager, true)

        dm.getUserStoriesForGame()
            .subscribe {
                showNextStory(it)
            }

        dm.getGameEnd()
            .subscribe {
                activity?.finish()
            }
    }

    private fun showNextStory(story: Story) {
        storyDialog = AlertDialog.Builder(activityContext)
            .setTitle("Current Story")
            .setMessage(story.title)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        storyDialog.show()
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
