package com.cameronvwilliams.raise.ui.pending.views


import android.os.Bundle
import android.support.v4.util.Pair
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.pending.presenter.PlayerListPresenter
import com.cameronvwilliams.raise.ui.pending.views.adapter.PlayerListAdapter
import io.reactivex.disposables.CompositeDisposable
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
        presenter.onViewCreated(this)

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
        fun newInstance(): PlayerListFragment {
            return PlayerListFragment()
        }
    }
}
