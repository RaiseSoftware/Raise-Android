package com.cameronvwilliams.raise.ui

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    abstract val currentTheme: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(currentTheme)
    }

    override fun onBackPressed() {
        val fragmentList = supportFragmentManager.fragments
        var backPressed = false
        for (fragment in fragmentList) {
            backPressed = (fragment as BaseFragment).onBackPressed()

            if (backPressed) {
                break
            }
        }

        if (!backPressed) {
            super.onBackPressed()
        }
    }
}