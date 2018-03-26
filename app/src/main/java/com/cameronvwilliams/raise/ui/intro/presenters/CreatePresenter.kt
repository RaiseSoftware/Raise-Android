package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.IntroContract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

class CreatePresenter(private val navigator: Navigator, private val dm: DataManager) :
    IntroContract.CreateUserActions {

    override lateinit var actions: IntroContract.CreateViewActions

    private val disposables = CompositeDisposable()
    private lateinit var createdPokerGame: PokerGame
    private lateinit var selectedUserName: String

    override fun onViewDestroyed() {
        disposables.clear()
    }

    override fun onCreateButtonClicked(gameName: String, userName: String, selectedDeckType: Int, requirePasscode: Boolean) {
        val deckType: DeckType = when (selectedDeckType) {
            actions.RADIO_FIBONACCI -> DeckType.FIBONACCI
            actions.RADIO_T_SHIRT -> DeckType.T_SHIRT
            else -> throw IllegalArgumentException()
        }

        val disposable: Disposable = dm.createPokerGame(PokerGame(gameName, deckType, requirePasscode), Player(userName))
            .doOnSubscribe {
                actions.showLoadingView()
            }
            .subscribe({ pokerGame ->
                actions.hideLoadingView()
                if (actions.shouldShowInterstitialAd()) {
                    createdPokerGame = pokerGame
                    selectedUserName = userName
                    actions.showInterstitialAd()
                } else {
                    navigator.goToPendingView(pokerGame, userName, true)
                }
            }, { e ->
                Timber.e(e)
                actions.hideLoadingView()
                actions.showDefaultErrorSnackBar()
            })

        disposables.add(disposable)
    }

    override fun onGameNameTextChanged(text: String, radioButtonSelected: Boolean) {
        if (!text.isEmpty() && radioButtonSelected) {
            actions.enableCreateButton()
        } else {
            actions.disableCreateButton()
        }
    }

    override fun onDeckTypeRadioButtonChecked(text: String, radioButtonSelected: Boolean) {
        if (!text.isEmpty() && radioButtonSelected) {
            actions.enableCreateButton()
        } else {
            actions.disableCreateButton()
        }
    }

    override fun onBackPressed(): Boolean {
        navigator.goBack()

        return true
    }

    override fun onAdClosed() {
        navigator.goToPendingView(createdPokerGame, selectedUserName, true)
    }
}
