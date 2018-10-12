package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.views.HtmlFragment

class HtmlPresenter(val navigator: Navigator): BasePresenter() {

    private lateinit var view: HtmlFragment

    fun onViewCreated(v: BaseFragment, viewType: String?) {
        super.onViewCreated(v)
        view = v as HtmlFragment

        when (viewType) {
            HtmlFragment.EXTRA_PRIVACY_POLICY -> view.setPrivacyPolicy()
            HtmlFragment.EXTRA_TERMS -> view.setTerms()
        }

        view.toolBarNavigationClicks()
            .subscribe {
                navigator.goBack()
            }
    }

    override fun onBackPressed(): Boolean {
        navigator.goBack()
        return true
    }
}