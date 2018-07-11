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
            .flatMapSingle {
                onJoinClicked(it)
            }
            .doOnEach {
                view.showLoadingView()
                view.disableJoinButton()
            }
            .subscribe({ result: Result ->
                when (result.type) {
                    JoinPresenter.ResultType.SUCCESS -> onJoinSuccess(result.data!!.first!!, result.data.second.name)
                    JoinPresenter.ResultType.FAILURE -> onJoinFailure(result.throwable!!, result.data!!.second.gameId, result.data.second.name)
                }
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
            dm.findPokerGame(details.gameId, details.name, it.passcode)
        } ?: dm.findPokerGame(details.gameId, details.name)

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
        val errorMessage = (t as RetrofitException).getErrorBodyAs(ErrorResponse::class.java)
        when (t.kind) {
            RetrofitException.Kind.HTTP -> {
                when (errorMessage?.statusCode) {
                    dm.CODE_FORBIDDEN -> navigator.goToPasscode(gameId, Player(userName))
                    dm.CODE_NOT_FOUND -> view.showErrorSnackBar(errorMessage.message)
                }
            }
            RetrofitException.Kind.NETWORK -> view.showDefaultErrorSnackBar()
            RetrofitException.Kind.UNEXPECTED -> view.showDefaultErrorSnackBar()
        }
    }

    private data class JoinDetails(val name: String, val gameId: String, val pokerGame: PokerGame?) {
        private val minimumGameIdLength = 5

        fun hasSuccessfullyScanned(): Boolean {
            return pokerGame != null
        }

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

    private data class Result(val type: ResultType, val data: Pair<PokerGame?, JoinDetails>? = null, val throwable: Throwable? = null)

    private enum class ResultType {
        SUCCESS,
        FAILURE
    }
}