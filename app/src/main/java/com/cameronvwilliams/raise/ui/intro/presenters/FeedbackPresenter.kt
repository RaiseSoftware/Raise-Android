package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.views.FeedbackFragment


class FeedbackPresenter(private val navigator: Navigator): BasePresenter() {

    lateinit var view: FeedbackFragment

    override fun onViewCreated(v: BaseFragment) {
        super.onViewCreated(v)
        view = v as FeedbackFragment

        val navigationClicks = view.toolBarNavigationClicks()
            .subscribe {
                navigator.goBack()
            }

        val rateUsClicks = view.rateUsClicks()
            .subscribe {
                navigator.goToPlayStore()
            }

        val issueClicks = view.issueClicks()
            .subscribe {
                navigator.goToGithubIssues()
            }

        viewSubscriptions.addAll(navigationClicks, rateUsClicks, issueClicks)
    }
}