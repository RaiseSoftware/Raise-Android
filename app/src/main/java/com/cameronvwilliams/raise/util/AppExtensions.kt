package com.cameronvwilliams.raise.util

import android.text.Editable
import android.text.TextWatcher
import android.view.SurfaceHolder
import android.widget.EditText
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun SurfaceHolder.callbacks(
    onCreated: (SurfaceHolder?) -> Unit,
    onDestroyed: (SurfaceHolder?) -> Unit
) {
    this.addCallback(object : SurfaceHolder.Callback {
        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {}

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            onDestroyed(holder)
        }

        override fun surfaceCreated(holder: SurfaceHolder?) {
            onCreated(holder)
        }
    })
}

fun BarcodeDetector.onDetect(cb: (Detector.Detections<Barcode>) -> Unit) {
    return this.setProcessor(object : Detector.Processor<Barcode> {
        override fun release() {}

        override fun receiveDetections(detections: Detector.Detections<Barcode>) {
            cb(detections)
        }
    })
}

fun <T> Gson.fromJsonArray(json: String): List<T> {
    return this.fromJson<List<T>>(json, object : TypeToken<List<T>>() {}.type)
}

fun InterstitialAd.onAdClosed(cb: () -> Unit) {
    this.adListener = object: AdListener() {
        override fun onAdLoaded() {}

        override fun onAdFailedToLoad(errorCode: Int) {}

        override fun onAdOpened() {}

        override fun onAdLeftApplication() {}

        override fun onAdClosed() {
            cb()
        }
    }
}