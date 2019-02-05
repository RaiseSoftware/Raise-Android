package com.cameronvwilliams.raise.ui.poker.presenter

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.poker.views.PokerCardFragment
import io.reactivex.rxkotlin.plusAssign

class PokerCardPresenter(val dm: DataManager) : BasePresenter() {

    lateinit var view: PokerCardFragment

    override fun onViewCreated(v: BaseFragment) {
        super.onViewCreated(v)
        view = v as PokerCardFragment

        val cardClicks = view.cardClicks()
            .subscribe {
                view.setActiveCard(it)
            }

        viewSubscriptions += cardClicks
    }

    fun onResume() {
        dm.joinGame("", Player())
    }
}