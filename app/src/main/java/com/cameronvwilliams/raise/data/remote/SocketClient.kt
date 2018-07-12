package com.cameronvwilliams.raise.data.remote

import android.support.v4.util.Pair
import android.support.v7.util.DiffUtil
import com.cameronvwilliams.raise.data.model.ActiveCard
import com.cameronvwilliams.raise.data.model.Card
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.event.*
import com.cameronvwilliams.raise.util.ActiveCardDiffCallback
import com.cameronvwilliams.raise.util.PlayerDiffCallback
import com.google.gson.Gson
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import okhttp3.OkHttpClient
import org.json.JSONObject

class SocketClient(val gson: Gson, okHttpClient: OkHttpClient, private val url: String): SocketAPI {

    private var socket: Socket? = null
    private var joinLeaveSubject: BehaviorSubject<SocketEvent>? = null
    private var cardSubmitSubject: BehaviorSubject<SocketEvent>? = null
    private var startGameSubject: BehaviorSubject<SocketEvent>? = null
    private var endGameSubject: BehaviorSubject<SocketEvent>? = null

    init {
        IO.setDefaultOkHttpCallFactory(okHttpClient)
        IO.setDefaultOkHttpWebSocketFactory(okHttpClient)
    }

    override fun connect(token: String) {
        if (socket == null || !socket!!.connected()) {
            val opts = IO.Options()
            opts.query = "token=$token"
            opts.secure = true
            opts.transports = arrayOf(WebSocket.NAME)
            opts.path = "/api/socket.io"

            socket = IO.socket(url, opts)
            initializeSocketListeners()
            socket?.connect()
        }
    }

    override fun disconnect() {
        socket?.off()
        socket?.disconnect()
    }

    override fun sendStartGameMessage() {
        socket?.emit(START_GAME_EVENT)
    }

    override fun sendSubmitCardMessage(card: Card) {
        socket?.emit(CARD_SUBMIT_EVENT, JSONObject(gson.toJson(card)))
    }

    override fun sendEndGameMessage() {
        socket?.emit(END_GAME_EVENT)
    }

    override fun onGameStart(): Completable {
        return startGameSubject!!.flatMapCompletable { _ ->
            Completable.complete()
        }
    }

    override fun onGameEnd(): Completable {
        return endGameSubject!!.flatMapCompletable { _ ->
            Completable.complete()
        }
    }

    override fun onPlayersInGameChange(): Flowable<Pair<List<Player>, DiffUtil.DiffResult>> {
        val emptyList: List<Player> = ArrayList()
        val initialPair: Pair<List<Player>, DiffUtil.DiffResult> = Pair.create(emptyList, null)

        return joinLeaveSubject!!.hide()
            .map { event ->
                (event as JoinLeaveEvent).data
            }
            .scan(initialPair) { pair, next ->
                val callback = PlayerDiffCallback(pair.first!!, next)
                val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(callback, false)
                Pair.create(next, result)
            }
            .skip(1)
            .toFlowable(BackpressureStrategy.LATEST)
    }

    override fun onActiveCardSetChange(): Flowable<Pair<List<ActiveCard>, DiffUtil.DiffResult>> {
        val emptyList: List<ActiveCard> = ArrayList()
        val initialPair: Pair<List<ActiveCard>, DiffUtil.DiffResult> = Pair.create(emptyList, null)

        return cardSubmitSubject!!.switchMap { event ->
                Observable.just((event as CardSubmitEvent).data)
            }
            .scan(initialPair) { pair, next ->
                val callback = ActiveCardDiffCallback(pair.first!!, next)
                val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(callback, false)
                Pair.create(next, result)
            }
            .skip(1)
            .toFlowable(BackpressureStrategy.LATEST)
    }

    private fun initializeSocketListeners() {

        joinLeaveSubject = BehaviorSubject.create()
        cardSubmitSubject = BehaviorSubject.create()
        startGameSubject = BehaviorSubject.create()
        endGameSubject = BehaviorSubject.create()

        joinLeaveSubject!!.replay(1).refCount()
        cardSubmitSubject!!.replay(1).refCount()
        startGameSubject!!.replay(1).refCount()
        endGameSubject!!.replay(1).refCount()

        socket?.on(START_GAME_EVENT) { args ->
            val jsonString = args[0] as String
            val event = gson.fromJson(jsonString, StartGameEvent::class.java)
            startGameSubject!!.onNext(event)
            startGameSubject!!.onComplete()
            startGameSubject = BehaviorSubject.create()
            startGameSubject!!.replay(1).refCount()
        }

        socket?.on(JOIN_LEAVE_EVENT) { args ->
            val jsonString = args[0] as String
            val event = gson.fromJson(jsonString, JoinLeaveEvent::class.java)
            joinLeaveSubject!!.onNext(event)
        }

        socket?.on(CARD_SUBMIT_EVENT) { args ->
            val jsonString = args[0] as String
            val event = gson.fromJson(jsonString, CardSubmitEvent::class.java)
            cardSubmitSubject!!.onNext(event)
        }

        socket?.on(END_GAME_EVENT) { args ->
            val jsonString = args[0] as String
            val event = gson.fromJson(jsonString, EndGameEvent::class.java)
            endGameSubject!!.onNext(event)
            endGameSubject!!.onComplete()
            endGameSubject = BehaviorSubject.create()
            endGameSubject!!.replay(1).refCount()
        }
    }

    companion object {
        const val JOIN_LEAVE_EVENT = "join-leave-game"
        const val CARD_SUBMIT_EVENT = "card-submit"
        const val START_GAME_EVENT = "start-game"
        const val END_GAME_EVENT = "end-game"
    }
}