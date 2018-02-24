package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.ErrorResponse
import com.cameronvwilliams.raise.data.remote.RetrofitException
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.IntroContract
import timber.log.Timber

class PasscodePresenter(private val navigator: Navigator, private val dm: DataManager) :
    IntroContract.PasscodeUserActions {

    private val minimumPasscodeLength = 5

    override lateinit var actions: IntroContract.PasscodeViewActions

    override fun onPasscodeTextChanged(passcode: String) {
        if (passcode.length > minimumPasscodeLength) {
            actions.enableSubmitButton()
        } else {
            actions.disableSubmitButton()
        }
    }

    override fun onSubmitButtonClick(gameId: String, passcode: String, userName: String) {
        dm.findPokerGame(gameId, userName, passcode)
            .doOnSubscribe {
                actions.showLoadingView()
            }
            .subscribe({ pokerGame ->
                navigator.goToPendingView(pokerGame, userName)
                actions.hideLoadingView()
            }, { error ->
                Timber.e(error)
                actions.hideLoadingView()

                val errorMessage =
                    (error as RetrofitException).getErrorBodyAs(ErrorResponse::class.java)
                when (error.kind) {
                    RetrofitException.Kind.HTTP -> {
                        when (errorMessage?.statusCode) {
                            dm.CODE_NOT_FOUND -> actions.showErrorSnackBar(errorMessage.message)
                        }
                    }
                    RetrofitException.Kind.NETWORK -> actions.showDefaultErrorSnackBar()
                    RetrofitException.Kind.UNEXPECTED -> actions.showDefaultErrorSnackBar()
                }
            })
    }

}