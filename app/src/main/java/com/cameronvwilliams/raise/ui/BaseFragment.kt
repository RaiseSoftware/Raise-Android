package com.cameronvwilliams.raise.ui

import android.os.Bundle
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    open fun onBackPressed(): Boolean {
        return false
    }
}
