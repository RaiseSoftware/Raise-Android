package com.cameronvwilliams.raise.ui.poker.views.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.Card
import com.cameronvwilliams.raise.data.model.CardValue
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.poker_card_list_item.view.*

class CardListAdapter(private var cards: MutableList<Card>) : RecyclerView.Adapter<CardListAdapter.CardListViewHolder>() {

    private val clickSubject = PublishSubject.create<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.poker_card_list_item, parent, false)

        return CardListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: CardListViewHolder, position: Int) {
        holder.bindCard(cards[position])
    }

    fun insertCardAt(index: Int, card: Card) {
        cards[index] = card.also {
            notifyItemInserted(index)
        }
    }

    fun removeCardAt(index: Int): Card {
        return cards.removeAt(index).also {
            notifyItemRemoved(index)
        }
    }

    fun updateList(newList: MutableList<Card>) {
        val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(object: DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return cards[oldItemPosition].value == newList[newItemPosition].value
            }

            override fun getOldListSize() = cards.size


            override fun getNewListSize() = newList.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return cards[oldItemPosition] == newList[newItemPosition]
            }
        })

        result.dispatchUpdatesTo(this)
        cards = newList
    }

    fun cardClicks(): Observable<Int> = clickSubject

    inner class CardListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.card
                .clicks()
                .subscribe {
                    clickSubject.onNext(layoutPosition)
                }
        }

        fun bindCard(card: Card) {
            when (card.value) {
                CardValue.X_SMALL -> itemView.card.setImageResource(R.drawable.card_xs)
                CardValue.SMALL -> itemView.card.setImageResource(R.drawable.card_s)
                CardValue.MEDIUM -> itemView.card.setImageResource(R.drawable.card_m)
                CardValue.LARGE -> itemView.card.setImageResource(R.drawable.card_l)
                CardValue.X_LARGE -> itemView.card.setImageResource(R.drawable.card_xl)
                CardValue.ZERO -> itemView.card.setImageResource(R.drawable.card_0)
                CardValue.ONE_HALF -> itemView.card.setImageResource(R.drawable.card_half)
                CardValue.ONE -> itemView.card.setImageResource(R.drawable.card_1)
                CardValue.TWO -> itemView.card.setImageResource(R.drawable.card_2)
                CardValue.THREE -> itemView.card.setImageResource(R.drawable.card_3)
                CardValue.FIVE -> itemView.card.setImageResource(R.drawable.card_5)
                CardValue.EIGHT -> itemView.card.setImageResource(R.drawable.card_8)
                CardValue.THIRTEEN -> itemView.card.setImageResource(R.drawable.card_13)
                CardValue.TWENTY -> itemView.card.setImageResource(R.drawable.card_20)
                CardValue.FORTY -> itemView.card.setImageResource(R.drawable.card_40)
                CardValue.ONE_HUNDRED -> itemView.card.setImageResource(R.drawable.card_100)
                CardValue.INFINITY -> itemView.card.setImageResource(R.drawable.card_infinity)
                CardValue.QUESTION_MARK -> itemView.card.setImageResource(R.drawable.card_question)
                CardValue.COFFEE -> itemView.card.setImageResource(R.drawable.card_coffee)
            }
        }
    }
}
