package com.cameronvwilliams.raise

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.DeepLinkHandler
import com.cameronvwilliams.raise.util.deeplink.RaiseDeepLinkModule
import com.cameronvwilliams.raise.util.deeplink.RaiseDeepLinkModuleLoader

@DeepLinkHandler(RaiseDeepLinkModule::class)
class DeeplinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val deepLinkDelegate = DeepLinkDelegate(RaiseDeepLinkModuleLoader())
        deepLinkDelegate.dispatchFrom(this)
        finish()
    }
}