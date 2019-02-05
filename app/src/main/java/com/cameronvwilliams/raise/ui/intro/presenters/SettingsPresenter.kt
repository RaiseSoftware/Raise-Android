package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.views.SettingsFragment

class SettingsPresenter(private val navigator: Navigator): BasePresenter() {

    private lateinit var view: SettingsFragment

    override fun onViewCreated(v: BaseFragment) {
        super.onViewCreated(v)
        view = v as SettingsFragment

        val aboutClicks = view.aboutClicks()
            .subscribe {
                navigator.goToAbout()
            }

        val feedbackClicks = view.feedbackClicks()
            .subscribe {
                navigator.goToFeedback()
            }

        val shareClicks = view.shareClicks()
            .subscribe {
                navigator.goToShareRaise()
            }

        viewSubscriptions.addAll(aboutClicks, feedbackClicks, shareClicks)
    }

    override fun onBackPressed(): Boolean {
        navigator.goBack()
        return true
    }
}