package com.cameronvwilliams.raise.ui.poker.views.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.Card
import com.cameronvwilliams.raise.data.model.CardValue
import kotlinx.android.synthetic.main.poker_card_list_item.view.*

class CardListViewHolder(view: View, private val onClick: (position: Int, view: View) -> Unit) :
    RecyclerView.ViewHolder(view) {

    fun bindCard(postion: Int, card: Card) {
        itemView.card.setOnClickListener { v ->
            onClick(postion, v)
        }

        when (card.value) {
            CardValue.X_SMALL.value -> itemView.card.setImageResource(R.drawable.card_0)
            CardValue.SMALL.value -> itemView.card.setImageResource(R.drawable.card_0)
            CardValue.MEDIUM.value -> itemView.card.setImageResource(R.drawable.card_0)
            CardValue.LARGE.value -> itemView.card.setImageResource(R.drawable.card_0)
            CardValue.X_LARGE.value -> itemView.card.setImageResource(R.drawable.card_0)
            CardValue.ZERO.value -> itemView.card.setImageResource(R.drawable.card_0)
            CardValue.ONE_HALF.value -> itemView.card.setImageResource(R.drawable.card_half)
            CardValue.ONE.value -> itemView.card.setImageResource(R.drawable.card_1)
            CardValue.TWO.value -> itemView.card.setImageResource(R.drawable.card_2)
            CardValue.THREE.value -> itemView.card.setImageResource(R.drawable.card_3)
            CardValue.FIVE.value -> itemView.card.setImageResource(R.drawable.card_5)
            CardValue.EIGHT.value -> itemView.card.setImageResource(R.drawable.card_8)
            CardValue.THIRTEEN.value -> itemView.card.setImageResource(R.drawable.card_13)
            CardValue.TWENTY.value -> itemView.card.setImageResource(R.drawable.card_20)
            CardValue.FORTY.value -> itemView.card.setImageResource(R.drawable.card_40)
            CardValue.ONE_HUNDRED.value -> itemView.card.setImageResource(R.drawable.card_100)
            CardValue.INFINITY.value -> itemView.card.setImageResource(R.drawable.card_infinity)
            CardValue.QUESTION_MARK.value -> itemView.card.setImageResource(R.drawable.card_question)
            CardValue.COFFEE.value -> itemView.card.setImageResource(R.drawable.card_0)
        }
    }
}