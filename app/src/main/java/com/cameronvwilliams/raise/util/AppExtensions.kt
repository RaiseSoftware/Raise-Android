package com.cameronvwilliams.raise.util

import android.view.SurfaceHolder
import android.view.View
import android.view.ViewTreeObserver
import com.cameronvwilliams.raise.data.model.Story
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable

fun Any.notNull(): Boolean {
    return this != null
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

fun BarcodeDetector.detections(): Observable<Detector.Detections<Barcode>> {
    return Observable.create { observer ->
        this.onDetect {
            observer.onNext(it)
        }
    }
}

fun <T> Gson.fromJsonArray(json: String): List<T> {
    return this.fromJson<List<T>>(json, object : TypeToken<List<T>>() {}.type)
}

fun <T: View> T.afterMeasured(f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

fun MutableList<Story>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}