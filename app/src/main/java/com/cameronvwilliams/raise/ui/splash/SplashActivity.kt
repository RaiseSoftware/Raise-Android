package com.cameronvwilliams.raise.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.Intent
import com.cameronvwilliams.raise.ui.intro.IntroActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, IntroActivity::class.java)
        startActivity(intent)
        finish()
    }
}
