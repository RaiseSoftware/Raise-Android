package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.IntroContract

class IntroPresenter(private val navigator: Navigator) : IntroContract.IntroUserActions {

    override lateinit var actions: IntroContract.IntroViewActions

    override fun onCreateButtonClicked() {
        navigator.goToCreateGame()
    }

    override fun onJoinButtonClicked() {
        navigator.goToJoinGame()
    }

    override fun onSettingsButtonClicked() {
        navigator.goToSettings()
    }
}