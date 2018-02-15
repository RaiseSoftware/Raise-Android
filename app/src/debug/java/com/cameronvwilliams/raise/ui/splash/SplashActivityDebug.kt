package com.cameronvwilliams.raise.ui.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cameronvwilliams.raise.ui.intro.IntroActivityDebug

class SplashActivityDebug : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, IntroActivityDebug::class.java)
        startActivity(intent)
        finish()
    }
}
