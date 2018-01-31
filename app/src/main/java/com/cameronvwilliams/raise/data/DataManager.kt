package com.cameronvwilliams.raise.data

import android.support.v4.util.Pair
import android.support.v7.util.DiffUtil
import com.cameronvwilliams.raise.data.model.*
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
    private val socketClient: SocketClient
) {

    val CODE_FORBIDDEN = 403
    val CODE_NOT_FOUND = 404

    fun createPokerGame(game: PokerGame): Observable<PokerGame> {
        return raiseAPI.createPokerGame(game)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun findPokerGame(gameId: String, passcode: String? = null): Observable<PokerGame> {
        return raiseAPI.findPokerGame(gameId, passcode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun joinGame(gameId: String, userName: String, passcode: String? = "") {
        socketClient.connect(gameId, userName, passcode)
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
            Card(DeckType.FIBONACCI.type, CardValue.ZERO.value),
            Card(DeckType.FIBONACCI.type, CardValue.ONE_HALF.value),
            Card(DeckType.FIBONACCI.type, CardValue.ONE.value),
            Card(DeckType.FIBONACCI.type, CardValue.TWO.value),
            Card(DeckType.FIBONACCI.type, CardValue.THREE.value),
            Card(DeckType.FIBONACCI.type, CardValue.FIVE.value),
            Card(DeckType.FIBONACCI.type, CardValue.EIGHT.value),
            Card(DeckType.FIBONACCI.type, CardValue.THIRTEEN.value),
            Card(DeckType.FIBONACCI.type, CardValue.TWENTY.value),
            Card(DeckType.FIBONACCI.type, CardValue.FORTY.value),
            Card(DeckType.FIBONACCI.type, CardValue.ONE_HUNDRED.value),
            Card(DeckType.FIBONACCI.type, CardValue.INFINITY.value),
            Card(DeckType.FIBONACCI.type, CardValue.QUESTION_MARK.value),
            Card(DeckType.FIBONACCI.type, CardValue.COFFEE.value)
        )
    }
}