package com.cameronvwilliams.raise.ui.pending.views


import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.pending.views.adapter.StoryListAdapter
import com.cameronvwilliams.raise.ui.pending.views.adapter.StoryListAdapter.SimpleItemTouchHelperCallback
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.pending_moderator_fragment.*
import javax.inject.Inject


class ModeratorFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: StoryListAdapter
    private lateinit var touchHelper: ItemTouchHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutManager = LinearLayoutManager(activity)
        adapter = StoryListAdapter(mutableListOf())
        val callback = SimpleItemTouchHelperCallback(adapter)
        touchHelper = ItemTouchHelper(callback)

        return inflater.inflate(R.layout.pending_moderator_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storyList.layoutManager = layoutManager
        storyList.adapter = adapter
        touchHelper.attachToRecyclerView(storyList)

        addStoryButton.setOnClickListener {
            navigator.showCreateStory(arguments?.get("game") as PokerGame) { items ->
                if (items.isNotEmpty()) {
                    heading.background = ContextCompat.getDrawable(context!!, R.drawable.background_grey_top_rounded)
                }
                adapter.onItemAdded(items.first())
            }
        }
    }

    fun addStoryClicks(): Observable<Unit> = addStoryButton.clicks()

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