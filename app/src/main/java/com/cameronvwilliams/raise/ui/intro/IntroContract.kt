package com.cameronvwilliams.raise.ui.intro

import com.cameronvwilliams.raise.ui.BaseUserActions

interface IntroContract {

    interface PasscodeViewActions {
        fun enableSubmitButton()
        fun disableSubmitButton()
        fun showLoadingView()
        fun hideLoadingView()
        fun showDefaultErrorSnackBar()
        fun showErrorSnackBar(message: String)
    }

    interface PasscodeUserActions : BaseUserActions<PasscodeViewActions> {
        fun onPasscodeTextChanged(passcode: String)
        fun onSubmitButtonClick(gameId: String, passcode: String, userName: String)
    }
}
