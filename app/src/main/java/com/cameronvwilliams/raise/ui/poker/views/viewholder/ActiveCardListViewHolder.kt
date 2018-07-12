package com.cameronvwilliams.raise.ui.poker.views.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.ActiveCard
import com.cameronvwilliams.raise.data.model.Card
import com.cameronvwilliams.raise.data.model.CardValue
import kotlinx.android.synthetic.main.poker_active_card_row_item.view.*

class ActiveCardListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindPlayer(activeCard: ActiveCard) {
        itemView.playerIcon.text = activeCard.player.name[0].toUpperCase().toString()
        itemView.userName.text = activeCard.player.name

        activeCard.card?.let {
            if (it.faceUp != null && it.faceUp!!) {
                setCardImage(activeCard.card)
            } else if (it.faceUp != null && !it.faceUp!!) {
                itemView.pokerCard.setImageResource(R.drawable.card_back)
            }
        }
    }

    private fun setCardImage(card: Card) {
        when (card.value) {
            CardValue.X_SMALL -> itemView.pokerCard.setImageResource(R.drawable.card_xs)
            CardValue.SMALL -> itemView.pokerCard.setImageResource(R.drawable.card_s)
            CardValue.MEDIUM -> itemView.pokerCard.setImageResource(R.drawable.card_m)
            CardValue.LARGE -> itemView.pokerCard.setImageResource(R.drawable.card_l)
            CardValue.X_LARGE -> itemView.pokerCard.setImageResource(R.drawable.card_xl)
            CardValue.ZERO -> itemView.pokerCard.setImageResource(R.drawable.card_0)
            CardValue.ONE_HALF -> itemView.pokerCard.setImageResource(R.drawable.card_half)
            CardValue.ONE -> itemView.pokerCard.setImageResource(R.drawable.card_1)
            CardValue.TWO -> itemView.pokerCard.setImageResource(R.drawable.card_2)
            CardValue.THREE -> itemView.pokerCard.setImageResource(R.drawable.card_3)
            CardValue.FIVE -> itemView.pokerCard.setImageResource(R.drawable.card_5)
            CardValue.EIGHT -> itemView.pokerCard.setImageResource(R.drawable.card_8)
            CardValue.THIRTEEN -> itemView.pokerCard.setImageResource(R.drawable.card_13)
            CardValue.TWENTY -> itemView.pokerCard.setImageResource(R.drawable.card_20)
            CardValue.FORTY -> itemView.pokerCard.setImageResource(R.drawable.card_40)
            CardValue.ONE_HUNDRED -> itemView.pokerCard.setImageResource(R.drawable.card_100)
            CardValue.INFINITY -> itemView.pokerCard.setImageResource(R.drawable.card_infinity)
            CardValue.QUESTION_MARK -> itemView.pokerCard.setImageResource(R.drawable.card_question)
            CardValue.COFFEE -> itemView.pokerCard.setImageResource(R.drawable.card_0)
        }
    }
}