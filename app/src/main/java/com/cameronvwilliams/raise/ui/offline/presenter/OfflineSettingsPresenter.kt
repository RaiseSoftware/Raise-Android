package com.cameronvwilliams.raise.ui.offline.presenter

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.offline.view.OfflineSettingsFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class OfflineSettingsPresenter @Inject constructor(
    private val dm: DataManager
) : BasePresenter() {

    private lateinit var view: OfflineSettingsFragment
    private val dataSubscriptions = CompositeDisposable()

    fun onViewCreated(v: OfflineSettingsFragment) {
        view = v

        view.setSelectedItem(dm.getOfflineDeckType().ordinal)

        viewSubscriptions += view.deckTypeSelections()
            .subscribe { index ->
                dm.setOfflineDeckType(DeckType.values()[index])
            }

        dataSubscriptions += dm.offlineDeckType()
            .subscribe { deckType -> view.setSelectedItem(deckType.ordinal) }
    }

    override fun onViewDestroyed() {
        super.onViewDestroyed()
        dataSubscriptions.clear()
    }
}