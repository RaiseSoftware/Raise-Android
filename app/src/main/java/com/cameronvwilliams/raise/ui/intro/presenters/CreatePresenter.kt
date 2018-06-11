package com.cameronvwilliams.raise.ui.intro.presenters

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.data.model.Token
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.views.CreateFragment
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.withLatestFrom
import timber.log.Timber

class CreatePresenter(private val navigator: Navigator, private val dm: DataManager): BasePresenter() {

    lateinit var view: CreateFragment

    private val disposables = CompositeDisposable()
    private lateinit var createdPokerGame: PokerGame
    private lateinit var selectedUserName: String

    override fun onViewCreated(v: BaseFragment) {
        super.onViewCreated(v)
        view = v as CreateFragment

        val backPresses = view.backPresses()
            .subscribe {
                onBackPressed()
            }

        val createFormDetails = Observables.combineLatest(
            view.deckTypeChanges(),
            view.nameChanges(),
            view.gameNameChanges(),
            view.passcodeChanges()
        ) { deckType: DeckType, userName: CharSequence, gameName: CharSequence, requirePasscode: Boolean ->
            CreateDetails(deckType, userName.toString(), gameName.toString(), requirePasscode)
        }.doOnNext {
            if (it.isValid()) {
                view.enableCreateButton()
            } else {
                view.disableCreateButton()
            }
        }

        val gameRequests = view.createGameRequests()
            .withLatestFrom(createFormDetails, { _, details ->
                details
            })
            .flatMapSingle { onCreateClicked(it) }
            .doOnEach {
                view.showLoadingView()
                view.disableCreateButton()
            }
            .subscribe({ pokerGame: PokerGame ->
                view.hideLoadingView()
                view.enableCreateButton()
                if (view.shouldShowInterstitialAd()) {
                    createdPokerGame = pokerGame
                    //selectedUserName = player.name
                    view.showInterstitialAd()
                } else {
                    //navigator.goToPendingView(pokerGame, player.name, true)
                }
            }, {
                Timber.e(it)
                view.hideLoadingView()
                view.showDefaultErrorSnackBar()
            })

        viewSubscriptions.addAll(gameRequests, backPresses)
    }

    override fun onViewDestroyed() {
        super.onViewDestroyed()
        disposables.clear()
    }

    private fun onCreateClicked(details: CreateDetails): Single<PokerGame> {
        val game = PokerGame(details.gameName, details.deckType, details.requirePasscode)
        val player = Player(details.userName)

        return dm.createPokerGame(game, player)
    }

    override fun onBackPressed(): Boolean {
        navigator.goBack()

        return true
    }

    fun onAdClosed() {
        navigator.goToPendingView(createdPokerGame, selectedUserName, true)
    }

    private data class CreateDetails(val deckType: DeckType?, val userName: String, val gameName: String, val requirePasscode: Boolean) {
        fun isValid(): Boolean {
            return deckType != DeckType.NONE && userName.isNotEmpty() && gameName.isNotEmpty()
        }
    }
}
