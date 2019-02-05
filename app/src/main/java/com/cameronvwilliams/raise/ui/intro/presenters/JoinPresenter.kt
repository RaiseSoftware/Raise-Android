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
import io.reactivex.exceptions.OnErrorNotImplementedException
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
            .subscribe {
                navigator.goToScannerView()
            }

        val joinFormDetails = Observables.combineLatest(
            view.nameChanges(),
            view.gameIdChanges(),
            navigator.scannerResult()
        ) { name: CharSequence, passcode: CharSequence, pokerGame: Navigator.Result<PokerGame> ->
            JoinDetails(name.toString(), passcode.toString(), pokerGame.data)
        }.distinctUntilChanged().doOnNext {
            when {
                it.isValid() -> view.enableJoinButton()
                it.hasSuccessfullyScanned() -> view.showQRCodeSuccessView()
                else -> view.disableJoinButton()
            }
        }

        val joinRequests = view.joinGameRequests()
            .withLatestFrom(joinFormDetails) { _, details ->
                details
            }
            .subscribe({ details ->
                navigator.goToPasscode(details.gameName, Player(dm.getUserId(), details.name, arrayListOf()))
            }, { t ->
                Timber.e(t)
                throw OnErrorNotImplementedException(t)
            })

        viewSubscriptions.addAll(backPresses, qrCodeRequests, joinRequests)
    }

    override fun onBackPressed(): Boolean {
        navigator.goBack()
        return true
    }

    private fun onJoinClicked(details: JoinDetails): Single<Result>? {
        val request = details.pokerGame?.let {
            dm.findGame(details.name, details.pokerGame.passcode)
        } ?: dm.findGame(details.gameName)

        return request.map { pokerGame ->
                Result(ResultType.SUCCESS, Pair(pokerGame, details))
            }
            .onErrorReturn { t ->
                Result(ResultType.FAILURE, Pair(null, details), t)
            }
    }

    private fun onJoinSuccess(pokerGame: PokerGame, userName: String) {
        view.hideLoadingView()
        view.enableJoinButton()
        pokerGame.startDateTime?.let {
            navigator.goToPokerGameView(pokerGame)
        } ?: navigator.goToPendingView(pokerGame, userName, false)
    }

    private fun onJoinFailure(t: Throwable, gameId: String, userName: String) {
        view.hideLoadingView()
        view.enableJoinButton()
        when (t) {
            else -> view.showDefaultErrorSnackBar()
        }
    }

    private data class JoinDetails(val name: String, val gameName: String, val pokerGame: PokerGame?) {
        fun hasSuccessfullyScanned(): Boolean {
            return pokerGame != null
        }

        fun isValid(): Boolean {
            pokerGame?.let {
                if (name.isNotEmpty()) {
                    return true
                }
            } ?: if (name.isNotEmpty() && gameName.isNotEmpty()) {
                return true
            }

            return false
        }
    }

    private data class Result(val type: ResultType, val data: Pair<PokerGame?, JoinDetails>? = null, val throwable: Throwable? = null)

    private enum class ResultType {
        SUCCESS,
        FAILURE
    }
}