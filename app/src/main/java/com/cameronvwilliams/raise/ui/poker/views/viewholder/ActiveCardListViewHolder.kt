package com.cameronvwilliams.raise.ui.poker.views.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.ActiveCard
import com.cameronvwilliams.raise.data.model.CardValue
import kotlinx.android.synthetic.main.poker_active_card_row_item.view.*

class ActiveCardListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindPlayer(activeCard: ActiveCard) {
        itemView.playerIcon.text = activeCard.player.name[0].toUpperCase().toString()
        itemView.userName.text = activeCard.player.name

        when (activeCard.card.value) {
            CardValue.X_SMALL.value -> itemView.pokerCard.setImageResource(R.drawable.card_0)
            CardValue.SMALL.value -> itemView.pokerCard.setImageResource(R.drawable.card_0)
            CardValue.MEDIUM.value -> itemView.pokerCard.setImageResource(R.drawable.card_0)
            CardValue.LARGE.value -> itemView.pokerCard.setImageResource(R.drawable.card_0)
            CardValue.X_LARGE.value -> itemView.pokerCard.setImageResource(R.drawable.card_0)
            CardValue.ZERO.value -> itemView.pokerCard.setImageResource(R.drawable.card_0)
            CardValue.ONE_HALF.value -> itemView.pokerCard.setImageResource(R.drawable.card_half)
            CardValue.ONE.value -> itemView.pokerCard.setImageResource(R.drawable.card_1)
            CardValue.TWO.value -> itemView.pokerCard.setImageResource(R.drawable.card_2)
            CardValue.THREE.value -> itemView.pokerCard.setImageResource(R.drawable.card_3)
            CardValue.FIVE.value -> itemView.pokerCard.setImageResource(R.drawable.card_5)
            CardValue.EIGHT.value -> itemView.pokerCard.setImageResource(R.drawable.card_8)
            CardValue.THIRTEEN.value -> itemView.pokerCard.setImageResource(R.drawable.card_13)
            CardValue.TWENTY.value -> itemView.pokerCard.setImageResource(R.drawable.card_20)
            CardValue.FORTY.value -> itemView.pokerCard.setImageResource(R.drawable.card_40)
            CardValue.ONE_HUNDRED.value -> itemView.pokerCard.setImageResource(R.drawable.card_100)
            CardValue.INFINITY.value -> itemView.pokerCard.setImageResource(R.drawable.card_infinity)
            CardValue.QUESTION_MARK.value -> itemView.pokerCard.setImageResource(R.drawable.card_question)
            CardValue.COFFEE.value -> itemView.pokerCard.setImageResource(R.drawable.card_0)
        }
    }
}