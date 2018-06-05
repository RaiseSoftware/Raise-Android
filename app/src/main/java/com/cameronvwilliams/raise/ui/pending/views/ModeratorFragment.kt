package com.cameronvwilliams.raise.ui.pending.views


import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.data.model.Story
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.pending.views.adapter.StoryListAdapter
import kotlinx.android.synthetic.main.pending_moderator_fragment.*
import javax.inject.Inject

class ModeratorFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: StoryListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutManager = LinearLayoutManager(activity)
        adapter = StoryListAdapter(listOf())

        return inflater!!.inflate(R.layout.pending_moderator_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storyList.layoutManager = layoutManager
        storyList.adapter = adapter

        addStoryButton.setOnClickListener {
            navigator.showCreateStory(arguments?.get("game") as PokerGame, { items ->
                if (items.isNotEmpty()) {
                    heading.background = ContextCompat.getDrawable(context!!, R.drawable.background_grey_top_rounded)
                }
                adapter.updateStoryList(items)
            })
        }
    }

    companion object {
        fun newInstance(game: PokerGame): ModeratorFragment {
            val fragment = ModeratorFragment()
            val args = Bundle()
            args.putParcelable("game", game)
            fragment.arguments = args
            return fragment
        }
    }
}