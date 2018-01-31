package com.cameronvwilliams.raise.ui.poker.views.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.Card
import com.cameronvwilliams.raise.ui.poker.views.viewholder.CardListViewHolder

class CardListAdapter(private var cards: List<Card>, private val cb: (position: Int, view: View) -> Unit) :
    RecyclerView.Adapter<CardListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardListViewHolder {
        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.card_list_item, parent, false)

        return CardListViewHolder(view, cb)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: CardListViewHolder?, position: Int) {
        holder?.bindCard(position, cards[position])
    }

    fun updateList(list: List<Card>) {
        this.cards = list
    }
}
