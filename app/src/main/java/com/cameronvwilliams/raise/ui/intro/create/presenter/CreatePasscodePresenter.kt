package com.cameronvwilliams.raise.ui.intro.create.presenter

import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.BasePresenter
import com.cameronvwilliams.raise.ui.Navigator
import com.cameronvwilliams.raise.ui.intro.create.CreatePasscodeFragment
import com.cameronvwilliams.raise.ui.intro.presenters.CreatePresenter
import io.reactivex.Single
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.rxkotlin.withLatestFrom
import timber.log.Timber
import javax.inject.Inject

class CreatePasscodePresenter @Inject constructor(val navigator: Navigator, val dm: DataManager) : BasePresenter() {

    lateinit var view: CreatePasscodeFragment
    lateinit var pokerGame: PokerGame
    lateinit var player: Player

    fun onViewCreated(v: BaseFragment, g: PokerGame, p: Player) {
        super.onViewCreated(v)
        view = v as CreatePasscodeFragment
        pokerGame = g
        player = p

        val passcodeChanges = view.passcodeChanges()
            .distinctUntilChanged()
            .doOnNext {
                if (it.isNotBlank() && it.length > 3) {
                    view.enableSubmitButton()
                } else {
                    view.disableSubmitButton()
                }
            }

        val backPresses = view.backPresses()
            .subscribe {
                onBackPressed()
            }

        val submitRequests = view.createRequests()
            .withLatestFrom(passcodeChanges) { _, passcode ->
                passcode.toString()
            }
            .doOnEach {
                view.showLoadingView()
                view.disableSubmitButton()
            }
            .flatMapSingle { onCreateClicked(it) }
            .subscribe({ result ->
                view.hideLoadingView()
                view.enableSubmitButton()
                when (result.type) {
                    ResultType.SUCCESS -> onCreateSuccess(result.data!!, p)
                    ResultType.FAILURE -> onCreateFailure()
                }
            }, { t ->
                throw OnErrorNotImplementedException(t)
            })

        viewSubscriptions.addAll(submitRequests, backPresses)
    }

    private fun onCreateClicked(details: String): Single<Result>? {
        pokerGame.passcode = details

        return dm.createGame(pokerGame)
            .map { pokerGame -> Result(ResultType.SUCCESS, pokerGame) }
            .onErrorReturn { t ->
                Timber.e(t)
                Result(ResultType.FAILURE, null)
            }
    }

    private fun onCreateSuccess(pokerGame: PokerGame, player: Player) {
        navigator.goToPending(pokerGame, player)
    }

    private fun onCreateFailure() {
        view.hideLoadingView()
    }

    override fun onBackPressed(): Boolean {
        navigator.goBack()
        return true
    }

    private data class Result(val type: ResultType, val data: PokerGame?)

    private enum class ResultType {
        SUCCESS,
        FAILURE
    }
}