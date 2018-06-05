package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.R.id.userName
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.ErrorResponse
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.data.remote.RetrofitException
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.IntroContract
import com.cameronvwilliams.raise.ui.intro.views.JoinFragment
import io.reactivex.disposables.CompositeDisposable
import org.reactivestreams.Subscription
import timber.log.Timber

class JoinPresenter(private val navigator: Navigator, private val dm: DataManager) : BasePresenter() {

    private lateinit var view: JoinFragment

    private val disposables = CompositeDisposable()
    private val minimumGameIdLength = 5

    override fun onViewDestroyed() {
        disposables.clear()
    }

    fun onJoinButtonClick(
        gameId: String,
        userName: String,
        pokerGame: PokerGame?
    ) {
        val request = pokerGame?.let { dm.findPokerGame(it.gameId!!, userName, pokerGame.passcode) }
                ?: dm.findPokerGame(gameId, userName)

        val subscription = request.doOnSubscribe {
            view.showLoadingView()
        }
            .doOnEvent { _, _ ->
                view.hideLoadingView()
            }
            .subscribe({ game ->
                navigator.goToPendingView(game, userName, false)
            }, { error ->
                Timber.e(error)
                val errorMessage =
                    (error as RetrofitException).getErrorBodyAs(ErrorResponse::class.java)
                when (error.kind) {
                    RetrofitException.Kind.HTTP -> {
                        when (errorMessage?.statusCode) {
                            dm.CODE_FORBIDDEN -> navigator.goToPasscode(gameId, Player(userName))
                            dm.CODE_NOT_FOUND -> view.showErrorSnackBar(errorMessage.message)
                        }
                    }
                    RetrofitException.Kind.NETWORK -> view.showDefaultErrorSnackBar()
                    RetrofitException.Kind.UNEXPECTED -> view.showDefaultErrorSnackBar()
                }
            })

        disposables.add(subscription)
    }

    fun onNameTextChanged(userName: String, gameId: String, pokerGame: PokerGame?) {
        validateFormData(userName, gameId, pokerGame)
    }

    fun onGameIdTextChanged(gameId: String, userName: String, pokerGame: PokerGame?) {
        validateFormData(userName, gameId, pokerGame)
    }

    override fun onBackPressed(): Boolean {
        navigator.goBack()
        return true
    }

    private fun validateFormData(userName: String, gameId: String, pokerGame: PokerGame?) {
        if (pokerGame == null) {
            if (userName.isNotBlank() && gameId.isNotBlank() && gameId.length > minimumGameIdLength) {
                view.enableJoinButton()
            } else {
                view.disableJoinButton()
            }
        } else {
            if (userName.isNotBlank()) {
                view.enableJoinButton()
            } else {
                view.disableJoinButton()
            }
        }
    }
}