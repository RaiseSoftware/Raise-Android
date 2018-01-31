package com.cameronvwilliams.raise.ui.intro.views

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.di.ActivityContext
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.IntroContract
import com.cameronvwilliams.raise.util.callbacks
import com.cameronvwilliams.raise.util.listeners
import com.cameronvwilliams.raise.util.onChange
import com.cameronvwilliams.raise.util.onDetect
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.karumi.dexter.Dexter
import kotlinx.android.synthetic.main.fragment_join_game.*
import timber.log.Timber
import javax.inject.Inject


class JoinFragment : BaseFragment(), IntroContract.JoinViewActions {

    @Inject
    lateinit var actions: IntroContract.JoinUserActions
    @Inject
    lateinit var navigator: Navigator
    @field:[Inject ActivityContext]
    lateinit var activityContext: Context

    private var pokerGame: PokerGame? = null
    private lateinit var cameraSource: CameraSource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val barcodeDetector = BarcodeDetector.Builder(activityContext)
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()

        barcodeDetector.onDetect { detections ->
            pokerGame = actions.onQrCodeDetect(detections, userNameEditText.text.toString().trim())
        }

        cameraSource = CameraSource.Builder(activityContext, barcodeDetector)
            .setRequestedPreviewSize(256, 256)
            .build()

        return inflater.inflate(R.layout.fragment_join_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actions.onViewCreated(this)

        backButton.setOnClickListener {
            actions.onBackPressed()
        }

        cameraView.holder.callbacks({ _ ->
            actions.onDetectorShow()
        }, {
            actions.onDetectorHide()
        })

        userNameEditText.onChange { s ->
            actions.onNameTextChanged(s, gameIdEditText.text.toString().trim(), pokerGame)
        }

        gameIdEditText.onChange { s ->
            actions.onGameIdTextChanged(s, userNameEditText.text.toString().trim(), pokerGame)
        }

        joinButton.setOnClickListener {
            actions.onJoinButtonClick(
                gameIdEditText.text.toString().trim(),
                userNameEditText.text.toString().trim(),
                pokerGame = pokerGame
            )
        }

        barcodeText.setOnClickListener {
            actions.onBarcodeTextClick()
            actions.onGameIdTextChanged(
                gameIdEditText.text.toString().trim(),
                userNameEditText.text.toString().trim(),
                pokerGame
            )
        }

        gameIdText.setOnClickListener {
            actions.onGameIdTextClick()
            actions.onGameIdTextChanged(
                gameIdEditText.text.toString().trim(),
                userNameEditText.text.toString().trim(),
                pokerGame
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        actions.onViewDestroyed()
    }

    override fun onBackPressed(): Boolean {
        return actions.onBackPressed()
    }

    override fun showLoadingView() {
        inputWrapper.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoadingView() {
        inputWrapper.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun showDefaultErrorSnackBar() {
        Snackbar.make(joinGameView, getString(R.string.error_network), Snackbar.LENGTH_LONG).show()
    }

    override fun showErrorSnackBar(message: String) {
        Snackbar.make(joinGameView, message, Snackbar.LENGTH_LONG).show()
    }

    override fun enableJoinButton() {
        joinButton.isEnabled = true
    }

    override fun disableJoinButton() {
        joinButton.isEnabled = true
    }

    override fun checkForPermissons() {
        Dexter.withActivity(activity)
            .withPermission(Manifest.permission.CAMERA)
            .listeners({
                actions.onPermissionGranted()
            }, {
                actions.onPermissionDenied()
            })
            .withErrorListener { error ->
                Timber.e(error.toString())
            }
            .check()
    }

    override fun showQRCodeView() {
        fillFormText.visibility = View.GONE
        formDivider.visibility = View.GONE
        gameIdEditText.visibility = View.GONE
        barcodeText.visibility = View.GONE

        scanQRCodeText.visibility = View.VISIBLE
        cameraView.visibility = View.VISIBLE
        gameIdText.visibility = View.VISIBLE
    }

    override fun showGameIdView() {
        fillFormText.visibility = View.VISIBLE
        formDivider.visibility = View.VISIBLE
        gameIdEditText.visibility = View.VISIBLE
        barcodeText.visibility = View.VISIBLE

        scanQRCodeText.visibility = View.GONE
        cameraView.visibility = View.GONE
        gameIdText.visibility = View.GONE
    }

    @Throws(SecurityException::class)
    override fun showCameraSource() {
        cameraSource.start(cameraView.holder)
    }

    override fun hideCameraSource() {
        cameraSource.release()
    }

    override fun showQRCodeSuccessView() {
        qrCodeSuccessText.visibility = View.VISIBLE
        checkMark.visibility = View.VISIBLE

        orDividerText.visibility = View.GONE
        scanQRCodeText.visibility = View.GONE
        cameraView.visibility = View.GONE
        gameIdText.visibility = View.GONE
    }

    companion object {
        fun newInstance(): JoinFragment {
            return JoinFragment()
        }
    }
}
