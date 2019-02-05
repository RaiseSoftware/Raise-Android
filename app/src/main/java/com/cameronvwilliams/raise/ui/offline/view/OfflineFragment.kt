package com.cameronvwilliams.raise.ui.offline.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.MainActivity
import com.cameronvwilliams.raise.ui.offline.presenter.OfflinePresenter
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.checkedChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.intro_offline_fragment.*
import javax.inject.Inject

class OfflineFragment: BaseFragment() {

    @Inject
    lateinit var window: MainActivity

    @Inject
    lateinit var presenter: OfflinePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_offline_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        window.setOfflineMode()
        presenter.onViewCreated(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDestroyed()
    }

    override fun onBackPressed(): Boolean {
        return presenter.onBackPressed()
    }

    fun closeClicks() = closeButton.clicks()

    fun startClicks() = startButton.clicks()

    fun deckTypeChanges(): Observable<DeckType> = deckTypeRadioGroup.checkedChanges()
        .map(this::mapRadioToDeckType)

    fun enableStartButton() {
        startButton.isEnabled = true
    }

    fun disableStartButton() {
        startButton.isEnabled = false
    }

    fun setSelectedDeckType(offlineDeckType: DeckType) {
        val selectedId = mapDeckTypeToRadio(offlineDeckType)
        selectedId?.let {
            deckTypeRadioGroup.check(it)
        }
    }

    @androidx.annotation.IdRes
    private fun mapDeckTypeToRadio(deckType: DeckType): Int? {
        return when (deckType) {
            DeckType.FIBONACCI -> R.id.fibonacciRadio
            DeckType.T_SHIRT -> R.id.tshirtRadio
            else -> null
        }
    }

    private fun mapRadioToDeckType(@IdRes radioId: Int): DeckType {
        return when (radioId) {
            R.id.fibonacciRadio -> DeckType.FIBONACCI
            R.id.tshirtRadio -> DeckType.T_SHIRT
            else -> DeckType.NONE
        }
    }

    companion object {
        fun newInstance(): OfflineFragment {
            return OfflineFragment()
        }
    }
}