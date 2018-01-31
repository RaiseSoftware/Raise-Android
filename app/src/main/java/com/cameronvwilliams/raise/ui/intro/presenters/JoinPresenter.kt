package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.ErrorResponse
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.data.remote.RetrofitException
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.IntroContract
import com.google.android.gms.internal.zzahn.runOnUiThread
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import timber.log.Timber
import java.io.IOException

class JoinPresenter(
    private val navigator: Navigator,
    private val dm: DataManager,
    private val gson: Gson
) :
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
            dm.findPokerGame(pokerGame.gameId, pokerGame.passcode)
        } else {
            dm.findPokerGame(gameId)
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

    override fun onQrCodeDetect(
        detections: Detector.Detections<Barcode>,
        userName: String
    ): PokerGame? {
        val barcodes = detections.detectedItems
        if (barcodes.size() != 0) {
            try {
                val game = gson.fromJson(barcodes.valueAt(0).displayValue, PokerGame::class.java)
                runOnUiThread {
                    actions.showQRCodeSuccessView()
                    if (userName.isNotBlank()) {
                        actions.enableJoinButton()
                    } else {
                        actions.disableJoinButton()
                    }
                }

                return game
            } catch (e: JsonSyntaxException) {
                Timber.e(e)
                actions.showDefaultErrorSnackBar()
                actions.showCameraSource()
            }
        }

        return null
    }

    override fun onBarcodeTextClick() {
        actions.checkForPermissons()
    }

    override fun onGameIdTextClick() {
        actions.showGameIdView()
        gameIdMode = true
    }

    override fun onPermissionGranted() {
        actions.showQRCodeView()
        gameIdMode = false
    }

    override fun onPermissionDenied() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed(): Boolean {
        navigator.goToIntro()
        return true
    }

    override fun onDetectorShow() {
        try {
            actions.showCameraSource()
        } catch (e: IOException) {
            Timber.e(e)
        } catch (e: SecurityException) {
            Timber.e(e)
        }
    }

    override fun onDetectorHide() {
        actions.hideCameraSource()
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