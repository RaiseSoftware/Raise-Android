package com.cameronvwilliams.raise.ui.pending.views.viewholder

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.cameronvwilliams.raise.data.model.Player
import kotlinx.android.synthetic.main.pending_player_row_item.view.*

class PlayerListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindPlayer(player: Player) {
        itemView.playerIcon.text = player.name[0].toUpperCase().toString()
        itemView.userName.text = player.name
    }
}