package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.ErrorResponse
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.data.remote.RetrofitException
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.views.JoinFragment
import io.reactivex.Single
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.withLatestFrom
import timber.log.Timber

class JoinPresenter(private val navigator: Navigator, private val dm: DataManager) : BasePresenter() {

    private lateinit var view: JoinFragment

    override fun onViewCreated(v: BaseFragment) {
        super.onViewCreated(v)
        view = v as JoinFragment

        val backPresses = view.backPresses()
            .subscribe {
                onBackPressed()
            }

        val qrCodeRequests = view.qrCodeScanRequests()
            .flatMap {
                navigator.goToScannerView()
            }

        val joinFormDetails = Observables.combineLatest(
            view.nameChanges(),
            view.gameIdChanges(),
            qrCodeRequests
        ) { name: CharSequence, passcode: CharSequence, g ->
            JoinDetails(name.toString(), passcode.toString(), g)
        }.doOnNext {
            if (it.isValid()) {
                view.enableJoinButton()
            } else {
                view.disableJoinButton()
            }
        }

        val joinRequests = view.joinGameRequests()
            .withLatestFrom(joinFormDetails, { _, details ->
                details
            })
            .flatMapSingle { onJoinClicked(it) }
            .doOnEach {
                view.showLoadingView()
                view.disableJoinButton()
            }
            .subscribe({ pokerGame ->
                view.hideLoadingView()
                view.enableJoinButton()
                // navigator.goToPendingView(pokerGame, userName, false)
            }, { error ->
                Timber.e(error)
                view.hideLoadingView()
                view.enableJoinButton()
                val errorMessage =
                    (error as RetrofitException).getErrorBodyAs(ErrorResponse::class.java)
                when (error.kind) {
                    RetrofitException.Kind.HTTP -> {
                        when (errorMessage?.statusCode) {
                            //dm.CODE_FORBIDDEN -> navigator.goToPasscode(gameId, Player(userName))
                            dm.CODE_NOT_FOUND -> view.showErrorSnackBar(errorMessage.message)
                        }
                    }
                    RetrofitException.Kind.NETWORK -> view.showDefaultErrorSnackBar()
                    RetrofitException.Kind.UNEXPECTED -> view.showDefaultErrorSnackBar()
                }
            })

        viewSubscriptions.addAll(backPresses, joinRequests)
    }

    override fun onBackPressed(): Boolean {
        navigator.goBack()
        return true
    }

    private fun onJoinClicked(details: JoinDetails): Single<PokerGame> {
        return details.pokerGame?.let {
            dm.findPokerGame(details.gameId, details.name, it.passcode)
        } ?: dm.findPokerGame(details.gameId, details.name)
    }

    private data class JoinDetails(val name: String, val gameId: String, val pokerGame: PokerGame?) {
        private val minimumGameIdLength = 5

        fun isValid(): Boolean {
            pokerGame?.let {
                if (name.isNotEmpty()) {
                    return true
                }
            } ?: if (name.isNotEmpty() && gameId.isNotEmpty() && gameId.length >= minimumGameIdLength) {
                return true
            }

            return false
        }
    }
}