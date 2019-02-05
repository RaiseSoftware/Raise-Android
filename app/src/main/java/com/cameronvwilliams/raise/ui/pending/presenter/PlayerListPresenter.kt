package com.cameronvwilliams.raise.ui.pending.presenter

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.pending.views.PlayerListFragment

class PlayerListPresenter(private val dm: DataManager): BasePresenter() {

    lateinit var view: PlayerListFragment

    override fun onViewCreated(v: BaseFragment) {
        super.onViewCreated(v)
        view = v as PlayerListFragment

//        dm.getPlayersInGame()
//            .subscribe { result ->
//                view.updatePlayerList(result)
//            }
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }
}