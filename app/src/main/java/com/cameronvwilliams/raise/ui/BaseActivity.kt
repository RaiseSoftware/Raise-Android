package com.cameronvwilliams.raise.ui

import android.app.ActivityManager.TaskDescription
import android.os.Bundle
import com.cameronvwilliams.raise.R
import dagger.android.support.DaggerAppCompatActivity
import android.graphics.BitmapFactory
import android.util.TypedValue

abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bm = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        val value = TypedValue()
        theme.resolveAttribute(R.attr.colorPrimaryDark, value, true)
        setTaskDescription(TaskDescription(getString(R.string.app_name), bm, value.data))
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