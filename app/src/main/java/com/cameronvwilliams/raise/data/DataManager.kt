package com.cameronvwilliams.raise.data

import android.support.v4.util.Pair
import android.support.v7.util.DiffUtil
import com.cameronvwilliams.raise.data.local.RaisePreferences
import com.cameronvwilliams.raise.data.model.*
import com.cameronvwilliams.raise.data.model.api.PokerGameBody
import com.cameronvwilliams.raise.data.remote.RaiseAPI
import com.cameronvwilliams.raise.data.remote.SocketClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject constructor(
    private val raiseAPI: RaiseAPI,
    private val socketClient: SocketClient,
    private val raisePreferences: RaisePreferences
) {

    val CODE_FORBIDDEN = 403
    val CODE_NOT_FOUND = 404

    fun createPokerGame(game: PokerGame, player: Player): Observable<PokerGame> {
        return raiseAPI.createPokerGame(PokerGameBody(game, player))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { response ->
                setGameToken(response.token.token)
            }
            .map{ response -> response.pokerGame }
    }

    fun findPokerGame(gameId: String, name: String, passcode: String? = null): Observable<PokerGame> {
        return raiseAPI.findPokerGame(gameId, name, passcode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { response ->
                setGameToken(response.token.token)
            }
            .map{ response -> response.pokerGame }
    }

    fun joinGame() {
        socketClient.connect(getGameToken())
    }

    fun leaveGame() {
        socketClient.disconnect()
    }

    fun startGame(pokerGame: PokerGame) {
        socketClient.sendStartGameMessage(pokerGame)
    }

    fun endGame(pokerGame: PokerGame) {
        socketClient.sendEndGameMessage(pokerGame)
    }

    fun submitCard(card: Card) {
        socketClient.sendSubmitCardMessage(card)
    }

    fun getPlayersInGame(): Observable<Pair<List<Player>, DiffUtil.DiffResult>> {
        return socketClient.onPlayersInGameChange()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getActivePlayersCards(): Observable<Pair<List<ActiveCard>, DiffUtil.DiffResult>> {
        return socketClient.onActiveCardSetChange()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getGameStart(): Observable<PokerGame> {
        return socketClient.onGameStart()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getGameEnd(): Observable<PokerGame> {
        return socketClient.onGameEnd()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFibonacciCards(): MutableList<Card> {
        return arrayListOf(
            Card(DeckType.FIBONACCI, CardValue.ZERO),
            Card(DeckType.FIBONACCI, CardValue.ONE_HALF),
            Card(DeckType.FIBONACCI, CardValue.ONE),
            Card(DeckType.FIBONACCI, CardValue.TWO),
            Card(DeckType.FIBONACCI, CardValue.THREE),
            Card(DeckType.FIBONACCI, CardValue.FIVE),
            Card(DeckType.FIBONACCI, CardValue.EIGHT),
            Card(DeckType.FIBONACCI, CardValue.THIRTEEN),
            Card(DeckType.FIBONACCI, CardValue.TWENTY),
            Card(DeckType.FIBONACCI, CardValue.FORTY),
            Card(DeckType.FIBONACCI, CardValue.ONE_HUNDRED),
            Card(DeckType.FIBONACCI, CardValue.INFINITY),
            Card(DeckType.FIBONACCI, CardValue.QUESTION_MARK),
            Card(DeckType.FIBONACCI, CardValue.COFFEE)
        )
    }

    fun getTShirtCards(): MutableList<Card> {
        return arrayListOf(
            Card(DeckType.T_SHIRT, CardValue.X_SMALL),
            Card(DeckType.T_SHIRT, CardValue.SMALL),
            Card(DeckType.T_SHIRT, CardValue.MEDIUM),
            Card(DeckType.T_SHIRT, CardValue.LARGE),
            Card(DeckType.T_SHIRT, CardValue.X_LARGE),
            Card(DeckType.T_SHIRT, CardValue.QUESTION_MARK),
            Card(DeckType.T_SHIRT, CardValue.COFFEE)
        )
    }

    private fun setGameToken(token: String) = raisePreferences.setCurrentGameToken(token)

    private fun getGameToken(): String = raisePreferences.getCurrentGameToken()
}