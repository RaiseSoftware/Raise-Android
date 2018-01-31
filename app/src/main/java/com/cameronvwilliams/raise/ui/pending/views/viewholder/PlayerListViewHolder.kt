package com.cameronvwilliams.raise.ui.pending.views.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.cameronvwilliams.raise.data.model.Player
import kotlinx.android.synthetic.main.player_row_item.view.*

class PlayerListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindPlayer(player: Player) {
        itemView.playerIcon.text = player.userName[0].toUpperCase().toString()
        itemView.userName.text = player.userName
    }
}