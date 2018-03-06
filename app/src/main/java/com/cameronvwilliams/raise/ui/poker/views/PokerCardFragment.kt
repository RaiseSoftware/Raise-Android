package com.cameronvwilliams.raise.ui.poker.views


import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.Card
import com.cameronvwilliams.raise.data.model.CardValue
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.poker.views.adapter.CardListAdapter
import kotlinx.android.synthetic.main.poker_card_fragment.*
import javax.inject.Inject
import kotlin.math.min

class PokerCardFragment : BaseFragment() {

    @Inject
    lateinit var dm: DataManager

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: CardListAdapter
    private lateinit var cards: MutableList<Card>
    private lateinit var activeCard: Card
    private var activeCardPosition: Int = -1
    private var yDelta = 0

    override fun onCreateView(
        inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.poker_card_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val originalY = (selectedCard.layoutParams as ConstraintLayout.LayoutParams).topMargin
        val originalX = (selectedCard.layoutParams as ConstraintLayout.LayoutParams).leftMargin

        val pokerGame = with(PokerCardFragment.BundleOptions) {
            arguments.getPokerGame()
        }

        when (pokerGame.deckType) {
            DeckType.FIBONACCI -> cards = dm.getFibonacciCards()
            DeckType.T_SHIRT -> cards = dm.getTShirtCards()
        }


        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adapter = CardListAdapter(cards, { position, clickedView ->
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

            when (activeCard.value) {
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

            val layoutParams = selectedCard.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.topMargin = originalY
            layoutParams.leftMargin = originalX
            selectedCard.layoutParams = layoutParams
            selectedCard.visibility = View.VISIBLE

            dm.submitCard(activeCard)

            adapter.updateList(cards.toList())
            adapter.notifyDataSetChanged()
        })

        selectedCard.setOnTouchListener { v, event ->
            val y = event.rawY.toInt()

            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    val lParams = v.layoutParams as ConstraintLayout.LayoutParams
                    yDelta = y - lParams.topMargin
                }
                MotionEvent.ACTION_UP -> {
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                }
                MotionEvent.ACTION_POINTER_UP -> {
                }
                MotionEvent.ACTION_MOVE -> {
                    val slideOutTop = AnimationUtils.loadAnimation(activity, R.anim.slide_out_top)

                    if (yDelta > 1000) {
                        v.startAnimation(slideOutTop)
                        v.visibility = View.INVISIBLE
                    } else {
                        val layoutParams = v.layoutParams as ConstraintLayout.LayoutParams
                        layoutParams.topMargin = min(y - yDelta, originalY)
                        v.layoutParams = layoutParams
                    }
                }
            }
            root.invalidate()
            true
        }

        cardList.layoutManager = layoutManager
        cardList.adapter = adapter
    }

    companion object BundleOptions {
        private const val EXTRA_POKER_GAME = "poker_game"

        fun newInstance(): PokerCardFragment {
            return PokerCardFragment()
        }

        fun Bundle.getPokerGame(): PokerGame {
            return getParcelable(PokerCardFragment.EXTRA_POKER_GAME)
        }

        fun Bundle.setPokerGame(game: PokerGame) {
            putParcelable(PokerCardFragment.EXTRA_POKER_GAME, game)
        }
    }
}
