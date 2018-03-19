package com.cameronvwilliams.raise.data.remote

import android.support.v4.util.Pair
import android.support.v7.util.DiffUtil
import com.cameronvwilliams.raise.R.id.userName
import com.cameronvwilliams.raise.data.model.*
import com.cameronvwilliams.raise.data.model.event.*
import com.cameronvwilliams.raise.util.ActiveCardDiffCallback
import com.cameronvwilliams.raise.util.PlayerDiffCallback
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.CompletableSource
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.net.URLEncoder
import javax.inject.Singleton

@Singleton
class SocketClient(val gson: Gson, okHttpClient: OkHttpClient, private val url: String) {

    private lateinit var socket: Socket
    private val joinLeaveSubject: BehaviorSubject<SocketEvent> = BehaviorSubject.create()
    private val cardSubmitSubject: BehaviorSubject<SocketEvent> = BehaviorSubject.create()
    private val startGameSubject: BehaviorSubject<SocketEvent> = BehaviorSubject.create()
    private val endGameSubject: BehaviorSubject<SocketEvent> = BehaviorSubject.create()

    init {
        IO.setDefaultOkHttpCallFactory(okHttpClient)
        IO.setDefaultOkHttpWebSocketFactory(okHttpClient)
        joinLeaveSubject.replay(1).refCount()
        cardSubmitSubject.replay(1).refCount()
        startGameSubject.replay(1).refCount()
        endGameSubject.replay(1).refCount()
    }

    fun connect(token: String) {
        val opts = IO.Options()
        opts.query = "token=$token"
        opts.secure = true
        opts.transports = arrayOf(WebSocket.NAME)

        socket = IO.socket(url, opts)
        initializeSocketListeners()
        socket.connect()
    }

    fun disconnect() {
        joinLeaveSubject.onComplete()
        cardSubmitSubject.onComplete()
        startGameSubject.onComplete()
        endGameSubject.onComplete()
        socket.off()
        socket.disconnect()
    }

    fun sendStartGameMessage() {
        socket.emit(START_GAME_EVENT)
    }

    fun sendSubmitCardMessage(card: Card) {
        socket.emit(CARD_SUBMIT_EVENT, JSONObject(gson.toJson(card)))
    }

    fun sendEndGameMessage() {
        socket.emit(END_GAME_EVENT)
    }

    fun onGameStart(): Observable<String> {
        return startGameSubject.switchMapSingle { _ ->
            Single.just("")
        }
    }

    fun onGameEnd(): Completable {
        return endGameSubject.flatMapCompletable {
            Observable.just("").ignoreElements()
        }
    }

    fun onPlayersInGameChange(): Observable<Pair<List<Player>, DiffUtil.DiffResult>> {
        val emptyList: List<Player> = ArrayList()
        val initialPair: Pair<List<Player>, DiffUtil.DiffResult> = Pair.create(emptyList, null)

        return joinLeaveSubject.hide()
            .map { event ->
                (event as JoinLeaveEvent).data
            }
            .scan(initialPair, { pair, next ->
                val callback = PlayerDiffCallback(pair.first!!, next)
                val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(callback, false)
                Pair.create(next, result)
            })
            .skip(1)
    }

    fun onActiveCardSetChange(): Observable<Pair<List<ActiveCard>, DiffUtil.DiffResult>> {
        val emptyList: List<ActiveCard> = ArrayList()
        val initialPair: Pair<List<ActiveCard>, DiffUtil.DiffResult> = Pair.create(emptyList, null)

        return cardSubmitSubject.switchMap { event ->
                Observable.just((event as CardSubmitEvent).data)
            }
            .scan(initialPair, { pair, next ->
                val callback = ActiveCardDiffCallback(pair.first!!, next)
                val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(callback, false)
                Pair.create(next, result)
            })
            .skip(1)
    }

    private fun initializeSocketListeners() {
        socket.on(START_GAME_EVENT, { args ->
            val jsonString = args[0] as String
            val event = gson.fromJson(jsonString, StartGameEvent::class.java)
            startGameSubject.onNext(event)
        })

        socket.on(JOIN_LEAVE_EVENT, { args ->
            val jsonString = args[0] as String
            val event = gson.fromJson(jsonString, JoinLeaveEvent::class.java)
            joinLeaveSubject.onNext(event)
        })

        socket.on(CARD_SUBMIT_EVENT, { args ->
            val jsonString = args[0] as String
            val event = gson.fromJson(jsonString, CardSubmitEvent::class.java)
            cardSubmitSubject.onNext(event)
        })

        socket.on(END_GAME_EVENT, { args ->
            val jsonString = args[0] as String
            val event = gson.fromJson(jsonString, EndGameEvent::class.java)
            endGameSubject.onNext(event)
        })
    }

    companion object {
        const val JOIN_LEAVE_EVENT = "join-leave-game"
        const val CARD_SUBMIT_EVENT = "card-submit"
        const val START_GAME_EVENT = "start-game"
        const val END_GAME_EVENT = "end-game"
    }
}