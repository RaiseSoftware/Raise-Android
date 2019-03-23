package com.cameronvwilliams.raise.ui.pending.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.pending.presenter.PlayerListPresenter
import com.cameronvwilliams.raise.ui.pending.views.adapter.PlayerListAdapter
import kotlinx.android.synthetic.main.pending_player_list_fragment.*
import javax.inject.Inject

class PlayerListFragment : BaseFragment() {

    @Inject
    lateinit var presenter: PlayerListPresenter

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: PlayerListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutManager = LinearLayoutManager(activity)
        adapter = PlayerListAdapter(listOf())

        return inflater.inflate(R.layout.pending_player_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated(this, arguments!!.getParcelable("game")!!)

        playerList.layoutManager = layoutManager
        playerList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDestroyed()
    }

    override fun onBackPressed(): Boolean {
        return presenter.onBackPressed()
    }

    fun updatePlayerList(result: Pair<List<Player>, DiffUtil.DiffResult>) {
        result.second!!.dispatchUpdatesTo(adapter)
        adapter.updatePlayerList(result.first!!)
    }

    companion object {
        fun newInstance(pokerGame: PokerGame): PlayerListFragment {
            val fragment = PlayerListFragment()
            val args = Bundle()
            args.putParcelable("game", pokerGame)
            fragment.arguments = args
            return fragment
        }
    }
}
