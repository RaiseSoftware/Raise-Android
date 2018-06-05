package com.cameronvwilliams.raise.ui.intro.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.net.Uri.fromParts
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.di.ActivityContext
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.IntroContract
import com.cameronvwilliams.raise.ui.intro.presenters.JoinPresenter
import com.cameronvwilliams.raise.util.onChange
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.intro_join_fragment.*
import permissions.dispatcher.*
import timber.log.Timber
import javax.inject.Inject


@RuntimePermissions
class JoinFragment : BaseFragment() {

    @Inject
    lateinit var presenter: JoinPresenter
    @Inject
    lateinit var navigator: Navigator
    @field:[Inject ActivityContext]
    lateinit var activityContext: Context
    @Inject
    lateinit var gson: Gson

    private var pokerGame: PokerGame? = null
    private var disposables = CompositeDisposable()
    private lateinit var barcodeDetector: BarcodeDetector


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        barcodeDetector = BarcodeDetector.Builder(activityContext)
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()

        return inflater.inflate(R.layout.intro_join_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated(this)

        backButton.setOnClickListener {
            presenter.onBackPressed()
        }

        userNameEditText.onChange { s ->
            presenter.onNameTextChanged(s, gameIdEditText.text.toString().trim(), pokerGame)
        }

        gameIdEditText.onChange { s ->
            presenter.onGameIdTextChanged(s, userNameEditText.text.toString().trim(), pokerGame)
        }

        joinButton.setOnClickListener {
            presenter.onJoinButtonClick(
                gameIdEditText.text.toString().trim(),
                userNameEditText.text.toString().trim(),
                pokerGame
            )
        }

        barcodeText.setOnClickListener {
            showScannerActivtyWithPermissionCheck()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDestroyed()
    }

    override fun onBackPressed(): Boolean {
        return presenter.onBackPressed()
    }

    fun showLoadingView() {
        inputWrapper.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoadingView() {
        inputWrapper.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    fun showDefaultErrorSnackBar() {
        Snackbar.make(joinGameView, getString(R.string.error_network), Snackbar.LENGTH_LONG).show()
    }

    fun showErrorSnackBar(message: String) {
        Snackbar.make(joinGameView, message, Snackbar.LENGTH_LONG).show()
    }

    fun enableJoinButton() {
        joinButton.isEnabled = true
    }

    fun disableJoinButton() {
        joinButton.isEnabled = false
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun showScannerActivty() {
        val disposable = navigator.goToScannerView().subscribe { pokerGame ->
            this.pokerGame = pokerGame
            if (userNameEditText.text.toString().trim().isNotEmpty()) {
                enableJoinButton()
            } else {
                disableJoinButton()
            }
            showQRCodeSuccessView()
            disposables.dispose()
        }
        disposables.add(disposable)
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    fun showRationaleForCamera(request: PermissionRequest) {
        AlertDialog.Builder(activityContext)
            .setTitle(getString(R.string.unable_to_scan))
            .setMessage(getString(R.string.unable_to_scan_long))
            .setPositiveButton(getString(R.string.go_to_settings), { dialog, _ ->
                val intent =  Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
                val uri = Uri.fromParts ("package", activityContext.packageName, null)
                intent.data = uri
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                startActivity(intent)
                dialog.dismiss()
            })
            .setNegativeButton(getString(android.R.string.cancel), { dialog, _ ->
                dialog.dismiss()
            })
            .show()
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraDenied() {
        Snackbar.make(joinGameView, "Unable to scan until permission is granted", Snackbar.LENGTH_LONG).show()
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAskAgain() {
        Snackbar.make(joinGameView, "Give permission in order to access the camera", Snackbar.LENGTH_LONG).show()
    }

    fun showQRCodeSuccessView() {
        qrCodeSuccessText.visibility = View.VISIBLE
        checkMark.visibility = View.VISIBLE
        scanQRCodeText.visibility = View.VISIBLE

        orDividerText.visibility = View.GONE
        fillFormText.visibility = View.GONE
        formDivider.visibility = View.GONE
        gameIdEditText.visibility = View.GONE
        barcodeText.visibility = View.GONE
    }

    companion object {
        fun newInstance(): JoinFragment {
            return JoinFragment()
        }
    }
}
