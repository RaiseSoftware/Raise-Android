package com.cameronvwilliams.raise.ui.scanner

import android.os.Bundle
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.Navigator
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ScannerActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scanner_activity)
        navigator.goToScanner()
    }
}
