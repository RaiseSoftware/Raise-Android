package com.cameronvwilliams.raise.ui.pending.views.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.ui.pending.views.viewholder.PlayerListViewHolder

class PlayerListAdapter(private var list: List<Player>) :
    RecyclerView.Adapter<PlayerListViewHolder>() {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PlayerListViewHolder?, position: Int) {
        holder?.bindPlayer(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlayerListViewHolder {
        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.player_row_item, parent, false)

        return PlayerListViewHolder(view)
    }

    fun updatePlayerList(players: List<Player>) {
        list = players
    }
}