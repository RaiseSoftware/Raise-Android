package com.cameronvwilliams.raise.ui.intro.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment

class CreatePasscodeFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_create_passcode_fragment, container, false)
    }

    companion object {
        fun newInstance(): CreatePasscodeFragment =
            CreatePasscodeFragment()
    }
}
