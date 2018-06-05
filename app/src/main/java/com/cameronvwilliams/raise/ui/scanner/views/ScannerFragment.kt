package com.cameronvwilliams.raise.ui.scanner.views

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
import java.io.IOException
import javax.inject.Inject
import android.app.Activity
import android.content.Intent
import com.cameronvwilliams.raise.util.afterMeasured

class ScannerFragment : BaseFragment() {

    @Inject
    lateinit var gson: Gson

    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        barcodeDetector = BarcodeDetector.Builder(activity)
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()

        barcodeDetector.onDetect { detections ->
            if (detections.detectedItems.size() != 0) {
                try {
                    val game = gson.fromJson(detections.detectedItems.valueAt(0).displayValue, PokerGame::class.java)
                    val returnIntent = Intent()
                    returnIntent.putExtra("POKER_GAME", game)
                    activity!!.setResult(Activity.RESULT_OK, returnIntent)
                    activity!!.finish()
                } catch (e: JsonSyntaxException) {
                    Timber.e(e)
                }
            }
        }

        return inflater!!.inflate(R.layout.scanner_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraView.afterMeasured {
            cameraSource = CameraSource.Builder(activity, barcodeDetector)
                .setRequestedPreviewSize(cameraView.height, cameraView.width)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setAutoFocusEnabled(true)
                .setRequestedFps(24.0f)
                .build()
        }


        cameraView.holder.callbacks({ _ ->
            try {
                cameraSource.start(cameraView.holder)
            } catch (e: IOException) {
                Timber.e(e)
            } catch (e: SecurityException) {
                Timber.e(e)
            }
        }, {
            cameraSource.release()
        })

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
