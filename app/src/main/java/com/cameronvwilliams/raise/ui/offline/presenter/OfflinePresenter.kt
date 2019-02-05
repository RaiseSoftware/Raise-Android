package com.cameronvwilliams.raise.ui.offline.presenter

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.offline.view.OfflineFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.withLatestFrom
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OfflinePresenter @Inject constructor(
    private val navigator: Navigator,
    private val dm: DataManager
) : BasePresenter() {

    private lateinit var view: OfflineFragment
    private val dataSubscriptions = CompositeDisposable()

    override fun onViewCreated(v: BaseFragment) {
        super.onViewCreated(v)
        view = v as OfflineFragment

        dataSubscriptions += dm.offlineDeckType()
            .subscribe(view::setSelectedDeckType)

        viewSubscriptions += view.closeClicks()
            .subscribe {
                navigator.goBack()
            }

        val deckTypeChanges = view.deckTypeChanges()
            .doOnNext { deckType ->
                if (deckType != DeckType.NONE) {
                    dm.setOfflineDeckType(deckType)
                    view.enableStartButton()
                } else {
                    view.disableStartButton()
                }
            }

        viewSubscriptions += view.startClicks()
            .throttleFirst(800, TimeUnit.MILLISECONDS)
            .withLatestFrom(deckTypeChanges) { _, deckType ->
                deckType
            }
            .subscribe({ deckType ->
                navigator.goToOffline(deckType)
            }, { t ->
                Timber.e(t)
                throw OnErrorNotImplementedException(t)
            })
    }

    override fun onViewDestroyed() {
        super.onViewDestroyed()
        dataSubscriptions.clear()
    }

    override fun onBackPressed(): Boolean {
        navigator.goBack()
        return true
    }
}