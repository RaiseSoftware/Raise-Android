package com.cameronvwilliams.raise.ui.scanner.views

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.util.callbacks
import com.cameronvwilliams.raise.util.onDetect
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.scanner_fragment.*
import timber.log.Timber
import javax.inject.Inject
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import com.cameronvwilliams.raise.di.ActivityContext
import com.cameronvwilliams.raise.util.afterMeasured
import io.reactivex.disposables.CompositeDisposable
import permissions.dispatcher.*

@RuntimePermissions
class ScannerFragment : BaseFragment() {

    @Inject
    lateinit var gson: Gson

    @field:[Inject ActivityContext]
    lateinit var activityContext: Context

    private var cameraSource: CameraSource? = null
    private lateinit var barcodeDetector: BarcodeDetector

    private var pokerGame: PokerGame? = null
    private var disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        barcodeDetector = BarcodeDetector.Builder(activity)
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()

        barcodeDetector.onDetect { detections ->
            if (detections.detectedItems.size() != 0) {
                try {
                    val game = gson.fromJson(detections.detectedItems.valueAt(0).displayValue, PokerGame::class.java)
                    val returnIntent = Intent()
                    returnIntent.putExtra("POKER_GAME_ID", game.gameId)
                    returnIntent.putExtra("POKER_GAME_PASSCODE", game.passcode)
                    activity?.setResult(Activity.RESULT_OK, returnIntent)
                    activity?.finish()
                } catch (e: JsonSyntaxException) {
                    Timber.e(e)
                }
            }
        }

        return inflater.inflate(R.layout.scanner_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraView.afterMeasured {
            cameraSource = CameraSource.Builder(activityContext, barcodeDetector)
                .setRequestedPreviewSize(cameraView.height, cameraView.width)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setAutoFocusEnabled(true)
                .setRequestedFps(24.0f)
                .build()
        }

        cameraView.holder.callbacks({ _ ->
            startCameraWithPermissionCheck()
        }, {
            cameraSource?.release()
        })

    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.CAMERA)
    fun startCamera() {
        cameraSource?.start(cameraView.holder)
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    fun showRationaleForCamera(request: PermissionRequest) {
        AlertDialog.Builder(activityContext)
            .setTitle(getString(R.string.unable_to_scan))
            .setMessage(getString(R.string.unable_to_scan_long))
            .setPositiveButton(getString(R.string.go_to_settings), { dialog, _ ->
                val intent =  Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
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
                activity?.finish()
            })
            .show()
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraDenied() {
        Snackbar.make(scannerView, "Unable to scan until permission is granted", Snackbar.LENGTH_LONG).show()
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAskAgain() {
        Snackbar.make(scannerView, "Give permission in order to access the camera", Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        barcodeDetector.release()
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): ScannerFragment {
            return ScannerFragment()
        }
    }
}
