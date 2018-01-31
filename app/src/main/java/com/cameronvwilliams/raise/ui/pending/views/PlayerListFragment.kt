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
import kotlinx.android.synthetic.main.fragment_player_list.*
import javax.inject.Inject

class PlayerListFragment : BaseFragment() {

    @Inject
    lateinit var dm: DataManager

    private lateinit var layoutManager: LinearLayoutManager
    private val adapter = PlayerListAdapter(listOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(activity)
        playerList.layoutManager = layoutManager
        playerList.adapter = adapter

        dm.getPlayersInGame()
            .subscribe { result ->
                adapter.updatePlayerList(result.first!!)
                result.second!!.dispatchUpdatesTo(adapter)
            }
    }

    companion object {
        fun newInstance(): PlayerListFragment {
            return PlayerListFragment()
        }
    }
}
