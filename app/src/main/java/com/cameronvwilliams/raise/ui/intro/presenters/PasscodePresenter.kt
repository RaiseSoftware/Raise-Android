package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.ErrorResponse
import com.cameronvwilliams.raise.data.remote.RetrofitException
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.views.PasscodeFragment
import timber.log.Timber

class PasscodePresenter(private val navigator: Navigator, private val dm: DataManager): BasePresenter() {

    private lateinit var view: PasscodeFragment

    private val minimumPasscodeLength = 5

    override fun onViewCreated(v: BaseFragment) {
        super.onViewCreated(v)
        view = v as PasscodeFragment
    }

    fun onPasscodeTextChanged(passcode: String) {
        if (passcode.length > minimumPasscodeLength) {
            view.enableSubmitButton()
        } else {
            view.disableSubmitButton()
        }
    }

    fun onSubmitButtonClick(gameId: String, passcode: String, userName: String) {
        dm.findPokerGame(gameId, userName, passcode)
            .doOnSubscribe {
                view.showLoadingView()
            }
            .subscribe({ pokerGame ->
                navigator.goToPendingView(pokerGame, userName, false)
                view.hideLoadingView()
            }, { error ->
                Timber.e(error)
                view.hideLoadingView()

                val errorMessage =
                    (error as RetrofitException).getErrorBodyAs(ErrorResponse::class.java)
                when (error.kind) {
                    RetrofitException.Kind.HTTP -> {
                        when (errorMessage?.statusCode) {
                            dm.CODE_NOT_FOUND -> view.showErrorSnackBar(errorMessage.message)
                        }
                    }
                    RetrofitException.Kind.NETWORK -> view.showDefaultErrorSnackBar()
                    RetrofitException.Kind.UNEXPECTED -> view.showDefaultErrorSnackBar()
                }
            })
    }

}