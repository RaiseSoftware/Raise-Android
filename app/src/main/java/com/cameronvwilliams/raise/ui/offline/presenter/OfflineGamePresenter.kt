package com.cameronvwilliams.raise.ui.offline.presenter

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.offline.view.OfflineGameFragment
import com.cameronvwilliams.raise.ui.offline.view.OfflineGameFragment.BundleOptions.getDeckType
import com.cameronvwilliams.raise.ui.poker.views.adapter.CardListAdapter
import com.cameronvwilliams.raise.util.whenNull
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class OfflineGamePresenter @Inject constructor(
    private val navigator: Navigator,
    private val dm: DataManager
) : BasePresenter() {

    private lateinit var view: OfflineGameFragment

    fun onCreateView(v: BaseFragment, cards: ArrayList<CardListAdapter.CardListItem>?) {
        view = v as OfflineGameFragment

        whenNull(cards) {
            dm.offlineDeckType()
                .subscribe { deckType ->
                    val pokerCards = when (deckType) {
                        DeckType.FIBONACCI -> dm.getFibonacciCards()
                        DeckType.T_SHIRT -> dm.getTShirtCards()
                        else -> throw IllegalStateException("Deck Type was not properly set")
                    }

                    val cardList = ArrayList(pokerCards
                        .map { card ->
                            CardListAdapter.CardListItem(card, CardListAdapter.CardState.UNSELECTED)
                        })

                    view.initializeCards(cardList)
                }
        }
    }

    override fun onViewCreated(v: BaseFragment) {
        super.onViewCreated(v)

        viewSubscriptions += view.cardClicks()
            .subscribe(view::setActiveCard)

        viewSubscriptions += view.settingsClicks()
            .subscribe { navigator.goToOfflineSettings() }

        viewSubscriptions += view.flipCardClicks()
            .subscribe { view.flipCard() }
    }

    override fun onBackPressed(): Boolean {
        navigator.goBack()
        return true
    }
}