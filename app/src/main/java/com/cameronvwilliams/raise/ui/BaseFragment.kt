package com.cameronvwilliams.raise.ui

import dagger.android.support.DaggerFragment
import android.os.Bundle



abstract class BaseFragment : DaggerFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    open fun onBackPressed(): Boolean {
        return false
    }
}
