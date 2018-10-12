package com.cameronvwilliams.raise.ui.offline

import android.os.Bundle
import android.view.WindowManager
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.ui.BaseActivity

class OfflineActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.offline_activity)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        navigator.goToOffline(DeckType.FIBONACCI)
    }
}
