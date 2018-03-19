package com.cameronvwilliams.raise.ui

import android.app.ActivityManager.TaskDescription
import android.content.Intent
import android.os.Bundle
import com.cameronvwilliams.raise.R
import dagger.android.support.DaggerAppCompatActivity
import android.graphics.BitmapFactory
import android.util.TypedValue
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bm = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        val value = TypedValue()
        theme.resolveAttribute(R.attr.colorPrimaryDark, value, true)
        setTaskDescription(TaskDescription(getString(R.string.app_name), bm, value.data))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        navigator.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        val fragmentList = supportFragmentManager.fragments.reversed()
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