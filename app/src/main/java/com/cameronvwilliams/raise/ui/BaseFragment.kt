package com.cameronvwilliams.raise.ui

import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {
    open fun onBackPressed(): Boolean {
        return false
    }
}
