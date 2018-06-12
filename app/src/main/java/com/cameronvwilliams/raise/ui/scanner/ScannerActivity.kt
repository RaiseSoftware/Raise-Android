package com.cameronvwilliams.raise.ui.scanner

import android.os.Bundle
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseActivity

class ScannerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scanner_activity)
        navigator.goToScanner()
    }
}
