package com.cameronvwilliams.raise.ui.pending.views


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.pending.views.adapter.PlayerListAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.pending_player_list_fragment.*
import javax.inject.Inject

class PlayerListFragment : BaseFragment() {

    @Inject
    lateinit var dm: DataManager

    private val disposables = CompositeDisposable()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: PlayerListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutManager = LinearLayoutManager(activity)
        adapter = PlayerListAdapter(listOf())
        val subscription = dm.getPlayersInGame()
            .subscribe { result ->
                adapter.updatePlayerList(result.first!!)
                result.second!!.dispatchUpdatesTo(adapter)
            }

        disposables.add(subscription)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pending_player_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerList.layoutManager = layoutManager
        playerList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    companion object {
        fun newInstance(): PlayerListFragment {
            return PlayerListFragment()
        }
    }
}
