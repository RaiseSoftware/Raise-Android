package com.cameronvwilliams.raise.ui.poker.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.Card
import com.cameronvwilliams.raise.data.model.CardValue
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.poker.presenter.PokerCardPresenter
import com.cameronvwilliams.raise.ui.poker.views.adapter.CardListAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.poker_card_fragment.*
import javax.inject.Inject

class PokerCardFragment : BaseFragment() {

    @Inject
    lateinit var presenter: PokerCardPresenter

    @Inject
    lateinit var dm: DataManager

    private lateinit var pager: ViewPager
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: CardListAdapter
    private lateinit var cards: MutableList<Card>
    private var activeCard: Card? = null
    private var activeCardPosition: Int = -1
    private var flipped = false
    private var originalY = 0f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        pager = container as ViewPager

        val pokerGame = with(PokerCardFragment.BundleOptions) {
            arguments!!.getPokerGame()
        }

        when (pokerGame.deckType) {
            DeckType.FIBONACCI -> cards = dm.getFibonacciCards()
            DeckType.T_SHIRT -> cards = dm.getTShirtCards()
            else -> {
            } // no-op
        }

        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adapter = CardListAdapter(cards)

        return inflater.inflate(R.layout.poker_card_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated(this)

        originalY = selectedCard.translationY

        sendCard.setOnClickListener {
            val displayMetrics = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels
            val sendAnimation = ValueAnimator.ofFloat(0f, -height.toFloat())
            sendAnimation.addUpdateListener {
                val value = it.animatedValue
                selectedCard.translationY = value as Float
            }

            sendAnimation.interpolator = AccelerateDecelerateInterpolator()
            sendAnimation.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    selectedCard.visibility = View.INVISIBLE
                    selectedCard.translationY = originalY
                    pager.setCurrentItem(1, true)
                    activeCard?.let {
                        dm.submitCard(it)
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }
            })
            sendAnimation.start()
        }

        flipCard.setOnClickListener {
            val oa1 = ObjectAnimator.ofFloat(selectedCard, "scaleX", 1f, 0f)
            val oa2 = ObjectAnimator.ofFloat(selectedCard, "scaleX", 0f, 1f)
            oa1.interpolator = DecelerateInterpolator()
            oa2.interpolator = AccelerateDecelerateInterpolator()
            oa1.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    if (flipped) {
                        activeCard?.let {
                            flipped = false
                            setSelectedCardImage(it)
                        }
                    } else {
                        flipped = true
                        selectedCard.setImageResource(R.drawable.card_back)
                    }
                    oa2.start()
                }
            })
            oa1.start()
        }

        cardList.layoutManager = layoutManager
        cardList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDestroyed()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    fun cardClicks(): Observable<Int> = adapter.cardClicks()

    fun setActiveCard(position: Int) {
        var tempPosition: Int = -1
        var tempCard: Card? = null

        flipped = false

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
