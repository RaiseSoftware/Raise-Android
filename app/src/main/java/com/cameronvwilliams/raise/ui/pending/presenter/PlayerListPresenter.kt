package com.cameronvwilliams.raise.ui.pending.presenter

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.pending.views.PlayerListFragment
import javax.inject.Inject

class PlayerListPresenter @Inject constructor(private val dm: DataManager): BasePresenter() {

    lateinit var view: PlayerListFragment

    fun onViewCreated(v: BaseFragment, pokerGame: PokerGame) {
        super.onViewCreated(v)
        view = v as PlayerListFragment

        dm.getPlayersInGame(pokerGame.uid!!)
            .subscribe { result ->
                view.updatePlayerList(result)
            }
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }
}