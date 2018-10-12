package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.views.AboutFragment

class AboutPresenter(private val navigator: Navigator): BasePresenter() {

    private lateinit var view: AboutFragment

    override fun onViewCreated(v: BaseFragment) {
        super.onViewCreated(v)
        view = v as AboutFragment

        val navigationClicks = view.toolBarNavigationClicks()
            .subscribe {
                navigator.goBack()
            }

        val privacyPolicyClicks = view.privacyPolicyClicks()
            .subscribe {
                navigator.goToPrivacyPolicy()
            }

        val termsClicks = view.termsClicks()
            .subscribe {
                navigator.goToTerms()
            }

        viewSubscriptions.addAll(navigationClicks, privacyPolicyClicks, termsClicks)
    }

    override fun onBackPressed(): Boolean {
        navigator.goBack()
        return true
    }
}