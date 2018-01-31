package com.cameronvwilliams.raise.ui.intro.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.intro.IntroContract
import kotlinx.android.synthetic.main.fragment_intro.*
import javax.inject.Inject

class IntroFragment : BaseFragment(), IntroContract.IntroViewActions {

    @Inject
    lateinit var actions: IntroContract.IntroUserActions

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actions.onViewCreated(this)

        createButton.setOnClickListener {
            actions.onCreateButtonClicked()
        }

        joinButton.setOnClickListener {
            actions.onJoinButtonClicked()
        }

        settingsButton.setOnClickListener {
            actions.onSettingsButtonClicked()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        actions.onViewDestroyed()
    }

    override fun onBackPressed(): Boolean {
        activity!!.finish()
        return true
    }

    companion object {
        fun newInstance(): IntroFragment {
            return IntroFragment()
        }
    }
}
