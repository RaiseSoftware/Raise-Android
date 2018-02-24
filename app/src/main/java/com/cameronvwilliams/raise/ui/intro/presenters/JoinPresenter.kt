package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.ErrorResponse
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.data.remote.RetrofitException
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.IntroContract
import timber.log.Timber

class JoinPresenter(
    private val navigator: Navigator,
    private val dm: DataManager) :
    IntroContract.JoinUserActions {

    override lateinit var actions: IntroContract.JoinViewActions

    private var gameIdMode = true
    private val minimumGameIdLength = 5

    override fun onJoinButtonClick(
        gameId: String,
        userName: String,
        passcode: String?,
        pokerGame: PokerGame?
    ) {
        val request = if (pokerGame != null) {
            dm.findPokerGame(pokerGame.gameId!!, userName, pokerGame.passcode)
        } else {
            dm.findPokerGame(gameId, userName)
        }

        request.doOnSubscribe {
            actions.showLoadingView()
        }
            .doOnEach {
                actions.hideLoadingView()
            }
            .subscribe({ game ->
                navigator.goToPendingView(game, userName)
            }, { error ->
                Timber.e(error)
                val errorMessage =
                    (error as RetrofitException).getErrorBodyAs(ErrorResponse::class.java)
                when (error.kind) {
                    RetrofitException.Kind.HTTP -> {
                        when (errorMessage?.statusCode) {
                            dm.CODE_FORBIDDEN -> navigator.goToPasscode(gameId, Player(userName))
                            dm.CODE_NOT_FOUND -> actions.showErrorSnackBar(errorMessage.message)
                        }
                    }
                    RetrofitException.Kind.NETWORK -> actions.showDefaultErrorSnackBar()
                    RetrofitException.Kind.UNEXPECTED -> actions.showDefaultErrorSnackBar()
                }
            })
    }

    override fun onNameTextChanged(userName: String, gameId: String, pokerGame: PokerGame?) {
        validateFormData(userName, gameId, pokerGame)
    }

    override fun onGameIdTextChanged(gameId: String, userName: String, pokerGame: PokerGame?) {
        validateFormData(userName, gameId, pokerGame)
    }

    override fun onBackPressed(): Boolean {
        navigator.goBack()
        return true
    }

    private fun validateFormData(userName: String, gameId: String, pokerGame: PokerGame?) {
        if (gameIdMode) {
            if (userName.isNotBlank() && gameId.isNotBlank() && gameId.length > minimumGameIdLength) {
                actions.enableJoinButton()
            } else {
                actions.disableJoinButton()
            }
        } else {
            if (userName.isNotBlank() && pokerGame != null) {
                actions.enableJoinButton()
            } else {
                actions.disableJoinButton()
            }
        }
    }
}