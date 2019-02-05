package com.cameronvwilliams.raise.ui.poker.views.adapter

import android.os.Parcel
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.Card
import com.cameronvwilliams.raise.data.model.CardValue
import com.cameronvwilliams.raise.util.Parcelable
import com.cameronvwilliams.raise.util.parcelableCreator
import com.cameronvwilliams.raise.util.readEnum
import com.cameronvwilliams.raise.util.writeEnum
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.poker_card_list_item.view.*

class CardListAdapter(private val cards: ArrayList<CardListItem>) : RecyclerView.Adapter<CardListAdapter.CardListViewHolder>() {

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

    fun cardClicks(): Observable<Int> = clickSubject

    fun selectCard(index: Int) {
        cards[index].state = CardState.SELECTED
        notifyItemChanged(index)
    }

    fun unselectCard(index: Int) {
        cards[index].state = CardState.UNSELECTED
        notifyItemChanged(index)
    }

    class CardListItem(val card: Card, var state: CardState): Parcelable {
        private constructor(parcel: Parcel) : this(
            card = parcel.readParcelable<Card>(Card::class.java.classLoader)!!,
            state = parcel.readEnum<CardState>()!!
        )

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            writeParcelable(card, flags)
            writeEnum(state)
        }

        companion object {
            @JvmField
            val CREATOR = parcelableCreator(::CardListItem)
        }

    }

    enum class CardState {
        SELECTED, UNSELECTED
    }

    inner class CardListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.card.setOnClickListener {
                clickSubject.onNext(layoutPosition)
            }
        }

        fun bindCard(item: CardListItem) {
            when (item.card.value) {
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

            when (item.state) {
                CardState.SELECTED -> itemView.card.alpha = .3f
                CardState.UNSELECTED -> itemView.card.alpha = 1f
            }
        }
    }
}
