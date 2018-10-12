package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.views.IntroFragment

class IntroPresenter(private val navigator: Navigator): BasePresenter() {

    lateinit var view: IntroFragment

    override fun onViewCreated(v: BaseFragment) {
        super.onViewCreated(v)
        view = v as IntroFragment

//        viewSubscriptions.add(view.createButtonClicks()
//            .subscribe {
//                navigator.goToCreateGame()
//            })
//
//        viewSubscriptions.add(view.joinButtonClicks()
//            .subscribe {
//                navigator.goToJoinGame()
//            })

        viewSubscriptions.add(view.settingsButtonClicks()
            .subscribe {
                navigator.goToSettings()
            })
    }
}