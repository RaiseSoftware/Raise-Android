package com.cameronvwilliams.raise.ui.pending.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.data.model.Story
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.pending.PendingActivity
import com.cameronvwilliams.raise.ui.poker.PokerActivity
import kotlinx.android.synthetic.main.pending_create_story_fragment.*
import java.io.Serializable
import javax.inject.Inject

class CreateStoryFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var dm: DataManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pending_create_story_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokerGame = arguments?.get("game") as PokerGame

        when (activity) {
            is PendingActivity -> {
                closeButton.setColorFilter(ContextCompat.getColor(context!!, R.color.pendingColorPrimary))
                addStoryButton.setTextColor(ContextCompat.getColor(context!!, R.color.pendingColorPrimary))
            }
            is PokerActivity -> {
                closeButton.setColorFilter(ContextCompat.getColor(context!!, R.color.pokerColorPrimary))
                addStoryButton.setTextColor(ContextCompat.getColor(context!!, R.color.pokerColorPrimary))
            }
        }

        closeButton.setOnClickListener {
            navigator.goBack()
        }

        addStoryButton.setOnClickListener {
            val story = Story(titleEditText.text.toString().trim())
//            dm.createUserStory(story, pokerGame.gameUuid!!)
//                .subscribe({ list ->
//                    val cb = arguments?.get("cb") as (MutableList<Story>) -> Unit
//                    cb(list)
//                    navigator.goBack()
//                }, { error ->
//                    Timber.e(error)
//                })
        }
    }

    override fun onBackPressed(): Boolean {
        navigator.goBack()
        return true
    }

    companion object {
        fun newInstance(game: PokerGame, cb: (MutableList<Story>) -> Unit): CreateStoryFragment {
            val fragment = CreateStoryFragment()
            val args = Bundle()
            args.putParcelable("game", game)
            args.putSerializable("cb", cb as Serializable)
            fragment.arguments = args
            return fragment
        }
    }
}
