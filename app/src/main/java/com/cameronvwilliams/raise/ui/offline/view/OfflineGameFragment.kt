package com.cameronvwilliams.raise.ui.offline.view


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.Card
import com.cameronvwilliams.raise.data.model.CardValue
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.poker.views.adapter.CardListAdapter
import kotlinx.android.synthetic.main.offline_game_fragment.*
import javax.inject.Inject

class OfflineGameFragment : BaseFragment() {

    @Inject
    lateinit var dm: DataManager

    @Inject
    lateinit var navigator: Navigator

    private lateinit var cards: MutableList<Card>
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: CardListAdapter

    private var activeCardPosition: Int = -1
    private var activeCard: Card? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        cards = dm.getFibonacciCards()
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adapter = CardListAdapter(cards)

        return inflater.inflate(R.layout.offline_game_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardList.layoutManager = layoutManager
        cardList.adapter = adapter

        settingsButton.setOnClickListener {
            navigator.goToOfflineSettings()
        }

        adapter.cardClicks()
            .subscribe {
                setActiveCard(it)
            }
    }

    fun setActiveCard(position: Int) {
        var tempPosition: Int = -1
        var tempCard: Card? = null

        if (activeCardPosition > -1) {
            tempPosition = activeCardPosition
            tempCard = activeCard
        }

        activeCardPosition = position
        activeCard = cards.removeAt(position)

        if (tempPosition > -1) {
            cards.add(tempPosition, tempCard!!)
        }

        activeCard?.let {
            setSelectedCardImage(it)
            selectedCard.visibility = View.VISIBLE
            it.faceUp = true
        }

        adapter.updateList(cards)
        adapter.notifyDataSetChanged()
    }

    private fun setSelectedCardImage(card: Card) {
        when (card.value) {
            CardValue.X_SMALL -> selectedCard.setImageResource(R.drawable.card_xs)
            CardValue.SMALL -> selectedCard.setImageResource(R.drawable.card_s)
            CardValue.MEDIUM -> selectedCard.setImageResource(R.drawable.card_m)
            CardValue.LARGE -> selectedCard.setImageResource(R.drawable.card_l)
            CardValue.X_LARGE -> selectedCard.setImageResource(R.drawable.card_xl)
            CardValue.ZERO -> selectedCard.setImageResource(R.drawable.card_0)
            CardValue.ONE_HALF -> selectedCard.setImageResource(R.drawable.card_half)
            CardValue.ONE -> selectedCard.setImageResource(R.drawable.card_1)
            CardValue.TWO -> selectedCard.setImageResource(R.drawable.card_2)
            CardValue.THREE -> selectedCard.setImageResource(R.drawable.card_3)
            CardValue.FIVE -> selectedCard.setImageResource(R.drawable.card_5)
            CardValue.EIGHT -> selectedCard.setImageResource(R.drawable.card_8)
            CardValue.THIRTEEN -> selectedCard.setImageResource(R.drawable.card_13)
            CardValue.TWENTY -> selectedCard.setImageResource(R.drawable.card_20)
            CardValue.FORTY -> selectedCard.setImageResource(R.drawable.card_40)
            CardValue.ONE_HUNDRED -> selectedCard.setImageResource(R.drawable.card_100)
            CardValue.INFINITY -> selectedCard.setImageResource(R.drawable.card_infinity)
            CardValue.QUESTION_MARK -> selectedCard.setImageResource(R.drawable.card_question)
            CardValue.COFFEE -> selectedCard.setImageResource(R.drawable.card_coffee)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = OfflineGameFragment()
    }
}
