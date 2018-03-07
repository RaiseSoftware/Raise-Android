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