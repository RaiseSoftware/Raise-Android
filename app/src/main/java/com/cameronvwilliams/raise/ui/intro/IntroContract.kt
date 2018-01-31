package com.cameronvwilliams.raise.ui.intro

import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.BaseUserActions
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode

interface IntroContract {
    interface IntroViewActions
    interface IntroUserActions : BaseUserActions<IntroViewActions> {
        fun onCreateButtonClicked()
        fun onJoinButtonClicked()
        fun onSettingsButtonClicked()
    }

    interface CreateViewActions {
        val RADIO_FIBONACCI: Int
        val RADIO_T_SHIRT: Int
        fun showLoadingView()
        fun hideLoadingView()
        fun showErrorMessage()
        fun enableCreateButton()
        fun disableCreateButton()
        fun showDefaultErrorSnackBar()
        fun showErrorSnackBar(message: String)
        fun showInterstitialAd()
        fun shouldShowInterstitialAd(): Boolean
    }

    interface CreateUserActions : BaseUserActions<CreateViewActions> {
        fun onCreateButtonClicked(gameName: String, userName: String, selectedDeckType: Int, requirePasscode: Boolean)
        fun onGameNameTextChanged(text: String, radioButtonSelected: Boolean)
        fun onDeckTypeRadioButtonChecked(text: String, radioButtonSelected: Boolean)
        fun onAdClosed()
    }

    interface JoinViewActions {
        fun enableJoinButton()
        fun disableJoinButton()
        fun showLoadingView()
        fun hideLoadingView()
        fun showDefaultErrorSnackBar()
        fun showErrorSnackBar(message: String)
        fun checkForPermissons()
        fun showQRCodeView()
        fun showCameraSource()
        fun hideCameraSource()
        fun showGameIdView()
        fun showQRCodeSuccessView()
    }

    interface JoinUserActions : BaseUserActions<JoinViewActions> {
        fun onNameTextChanged(userName: String, gameId: String, pokerGame: PokerGame?)
        fun onGameIdTextChanged(gameId: String, userName: String, pokerGame: PokerGame?)
        fun onQrCodeDetect(detections: Detector.Detections<Barcode>, userName: String): PokerGame?
        fun onBarcodeTextClick()
        fun onPermissionGranted()
        fun onPermissionDenied()
        fun onJoinButtonClick(
            gameId: String,
            userName: String,
            passcode: String? = null,
            pokerGame: PokerGame?
        )

        fun onDetectorShow()
        fun onDetectorHide()
        fun onGameIdTextClick()
    }

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
