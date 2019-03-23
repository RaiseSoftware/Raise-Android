package com.cameronvwilliams.raise.ui.offline.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.Card
import com.cameronvwilliams.raise.data.model.CardValue
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.MainActivity
import com.cameronvwilliams.raise.ui.offline.presenter.OfflineGamePresenter
import com.cameronvwilliams.raise.ui.poker.views.adapter.CardListAdapter
import com.cameronvwilliams.raise.util.whenNull
import com.evernote.android.state.State
import com.jakewharton.rxbinding2.view.clicks
import kotlinx.android.synthetic.main.offline_game_fragment.*
import javax.inject.Inject

class OfflineGameFragment : BaseFragment() {

    @Inject
    lateinit var presenter: OfflineGamePresenter

    @Inject
    lateinit var activityContext: MainActivity

    @Inject
    lateinit var dm: DataManager

    @State
    var cards: ArrayList<CardListAdapter.CardListItem>? = null

    @State
    var activeCardPosition: Int = -1

    @State
    var activeCard: Card? = null

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: CardListAdapter
    private val snapHelper = LinearSnapHelper()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter.onCreateView(this, cards)

        adapter = CardListAdapter(cards!!)
        layoutManager = LinearLayoutManager(activityContext, LinearLayoutManager.HORIZONTAL, false)
        return inflater.inflate(R.layout.offline_game_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated(this)

        cardList.layoutManager = layoutManager
        cardList.adapter = adapter
        snapHelper.attachToRecyclerView(cardList)

        whenNull(savedInstanceState) {
            val animation = AnimationUtils.loadLayoutAnimation(
                requireContext(), R.anim.card_set_entrance
            )
            cardList.layoutAnimation = animation
        }

        if (activeCardPosition > -1) setActiveCard(activeCardPosition)
    }

    override fun onBackPressed(): Boolean {
        return presenter.onBackPressed()
    }

    fun cardClicks() = adapter.cardClicks()

    fun settingsClicks() = settingsButton.clicks()

    fun flipCardClicks() = flipCard.clicks()

    fun initializeCards(cards: ArrayList<CardListAdapter.CardListItem>?) {
        this.cards = cards
    }

    fun setActiveCard(position: Int) {
        if (activeCardPosition > -1) {
            adapter.unselectCard(activeCardPosition)
        }

        activeCardPosition = position
        activeCard = cards!![position].card

        activeCard?.let {
            setSelectedCardImage(it)
            selectedCard.visibility = View.VISIBLE
            it.faceUp = true
        }

        adapter.selectCard(position)
    }

    fun flipCard() {
        val oa1 = ObjectAnimator.ofFloat(selectedCard, "scaleX", 1f, 0f)
        val oa2 = ObjectAnimator.ofFloat(selectedCard, "scaleX", 0f, 1f)

        oa1.interpolator = DecelerateInterpolator()
        oa2.interpolator = AccelerateDecelerateInterpolator()

        oa1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)

                activeCard?.let {
                    it.flip()
                    setSelectedCardImage(it)
                }
                oa2.start()
            }
        })

        oa1.start()
    }

    private fun setSelectedCardImage(card: Card) {
        when (card.faceUp) {
            false -> selectedCard.setImageResource(R.drawable.card_back)
            else -> when (card.value) {
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
    }

    companion object BundleOptions {
        @JvmStatic
        fun newInstance(deckType: DeckType): OfflineGameFragment {
            val fragment = OfflineGameFragment()
            val bundle = Bundle()

            bundle.putSerializable("DECK_TYPE", deckType)
            fragment.arguments = bundle

            return fragment
        }

        @JvmStatic
        fun Bundle.getDeckType(): DeckType {
            return getSerializable("DECK_TYPE") as DeckType
        }
    }
}
