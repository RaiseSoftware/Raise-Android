package com.cameronvwilliams.raise.ui.scanner

import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.scanner.views.ScannerFragment
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import timber.log.Timber

class ScannerPresenter(private val gson: Gson) : BasePresenter() {

    lateinit var view: ScannerFragment

    override fun onViewCreated(v: BaseFragment) {
        super.onViewCreated(v)
        view = v as ScannerFragment

        view.onQrCodeDetections()
            .subscribe({ detections ->
                try {
                    if (detections.detectedItems.size() > 0) {
                        val game = gson.fromJson(detections.detectedItems.valueAt(0).displayValue, PokerGame::class.java)
                        view.sendPokerGame(game)
                    }
                } catch (e: JsonSyntaxException) {
                    Timber.e(e)
                }
            }, {
                Timber.e(it)
            })
    }

    override fun onBackPressed(): Boolean {
        view.close()
        return true
    }
}