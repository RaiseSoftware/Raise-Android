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


        cards = dm.getFibonacciCards()
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
                CardValue.X_SMALL.value -> selectedCard.setImageResource(R.drawable.card_0)
                CardValue.SMALL.value -> selectedCard.setImageResource(R.drawable.card_0)
                CardValue.MEDIUM.value -> selectedCard.setImageResource(R.drawable.card_0)
                CardValue.LARGE.value -> selectedCard.setImageResource(R.drawable.card_0)
                CardValue.X_LARGE.value -> selectedCard.setImageResource(R.drawable.card_0)
                CardValue.ZERO.value -> selectedCard.setImageResource(R.drawable.card_0)
                CardValue.ONE_HALF.value -> selectedCard.setImageResource(R.drawable.card_half)
                CardValue.ONE.value -> selectedCard.setImageResource(R.drawable.card_1)
                CardValue.TWO.value -> selectedCard.setImageResource(R.drawable.card_2)
                CardValue.THREE.value -> selectedCard.setImageResource(R.drawable.card_3)
                CardValue.FIVE.value -> selectedCard.setImageResource(R.drawable.card_5)
                CardValue.EIGHT.value -> selectedCard.setImageResource(R.drawable.card_8)
                CardValue.THIRTEEN.value -> selectedCard.setImageResource(R.drawable.card_13)
                CardValue.TWENTY.value -> selectedCard.setImageResource(R.drawable.card_20)
                CardValue.FORTY.value -> selectedCard.setImageResource(R.drawable.card_40)
                CardValue.ONE_HUNDRED.value -> selectedCard.setImageResource(R.drawable.card_100)
                CardValue.INFINITY.value -> selectedCard.setImageResource(R.drawable.card_infinity)
                CardValue.QUESTION_MARK.value -> selectedCard.setImageResource(R.drawable.card_question)
                CardValue.COFFEE.value -> selectedCard.setImageResource(R.drawable.card_0)
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

    companion object {
        fun newInstance(): PokerCardFragment {
            return PokerCardFragment()
        }
    }
}
