package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.ErrorResponse
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.data.remote.RetrofitException
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.views.PasscodeFragment
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber

class PasscodePresenter(private val navigator: Navigator, private val dm: DataManager): BasePresenter() {

    private lateinit var view: PasscodeFragment

    override fun onViewCreated(v: BaseFragment) {
        super.onViewCreated(v)
        view = v as PasscodeFragment

        val backPresses = view.backPresses()
            .subscribe {
                navigator.goBack()
            }

        val qrCodeRequests = view.qrCodeScanRequests()
            .subscribe {
                navigator.goToScannerView()
            }

        val passcodeFormDetails = Observables.combineLatest(
            view.passocdeChanges(),
            navigator.scannerResult()
        ) { passcode: CharSequence, pokerGame: Navigator.Result<PokerGame> ->
            PasscodeDetails(passcode.toString(), pokerGame.data)
        }.distinctUntilChanged().doOnNext {
            when {
                it.isValid() -> view.enableSubmitButton()
                else -> view.disableSubmitButton()
            }
        }

        viewSubscriptions.addAll(backPresses, qrCodeRequests)
    }

    override fun onBackPressed(): Boolean {
        navigator.goBack()
        return true
    }

    fun onSubmitButtonClick(gameId: String, passcode: String, userName: String) {
        val subscription = dm.findGame(gameId, passcode)
            .doOnSubscribe {
                view.showLoadingView()
            }
//            .flatMapCompletable { game ->
//                dm.joinGame(game.id)
//            }
            .subscribe({ pokerGame ->
                navigator.goToPendingView(pokerGame, userName, false)
                view.hideLoadingView()
            }, { error ->
                Timber.e(error)
                view.hideLoadingView()
            })

        viewSubscriptions += subscription
    }

    private data class PasscodeDetails(val passcode: String, val pokerGame: PokerGame?) {
        private val minimumPasscodeLength = 5

        fun hasSuccessfullyScanned(): Boolean {
            return pokerGame != null
        }

        fun isValid(): Boolean {
            pokerGame?.let {
                return true
            } ?: if (passcode.isNotEmpty() && passcode.length >= minimumPasscodeLength) {
                return true
            }

            return false
        }
    }
}