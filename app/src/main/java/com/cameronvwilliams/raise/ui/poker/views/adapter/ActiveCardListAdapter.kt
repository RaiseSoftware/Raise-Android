package com.cameronvwilliams.raise.ui.poker.views.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.ActiveCard
import com.cameronvwilliams.raise.ui.poker.views.viewholder.ActiveCardListViewHolder

class ActiveCardListAdapter(private var list: List<ActiveCard>) :
    RecyclerView.Adapter<ActiveCardListViewHolder>() {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ActiveCardListViewHolder, position: Int) {
        holder.bindPlayer(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveCardListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.poker_active_card_row_item, parent, false)

        return ActiveCardListViewHolder(view)
    }

    fun updateActiveCardList(players: List<ActiveCard>) {
        list = players
    }
}