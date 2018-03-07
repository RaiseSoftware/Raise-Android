package com.cameronvwilliams.raise.data.remote

import android.support.v4.util.Pair
import android.support.v7.util.DiffUtil
import com.cameronvwilliams.raise.R.id.userName
import com.cameronvwilliams.raise.data.model.*
import com.cameronvwilliams.raise.data.model.event.*
import com.cameronvwilliams.raise.util.ActiveCardDiffCallback
import com.cameronvwilliams.raise.util.PlayerDiffCallback
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import okhttp3.OkHttpClient
import java.net.URLEncoder
import javax.inject.Singleton

@Singleton
class SocketClient(val gson: Gson, okHttpClient: OkHttpClient, private val url: String) {

    private lateinit var socket: Socket
    private val socketSubject: BehaviorSubject<SocketEvent> = BehaviorSubject.create()

    init {
        IO.setDefaultOkHttpCallFactory(okHttpClient)
        IO.setDefaultOkHttpWebSocketFactory(okHttpClient)
        socketSubject.replay(1).refCount()
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
        socketSubject.onComplete()
        socket.off()
        socket.disconnect()
    }

    fun sendStartGameMessage(pokerGame: PokerGame) {
        socket.emit(START_GAME_EVENT, pokerGame)
    }

    fun sendSubmitCardMessage(card: Card) {
        socket.emit(CARD_SUBMIT_EVENT, gson.toJson(card))
    }

    fun sendEndGameMessage(pokerGame: PokerGame) {
        socket.emit(END_GAME_EVENT, pokerGame)
    }

    fun onGameStart(): Observable<PokerGame> {
        return socketSubject.filter { event ->
                event.type == START_GAME_EVENT
            }.switchMap { event ->
                Observable.just(PokerGame("", DeckType.FIBONACCI, false))
            }
    }

    fun onGameEnd(): Observable<PokerGame> {
        return socketSubject.filter { event ->
                event.type == START_GAME_EVENT
            }.switchMap { event ->
                Observable.just(PokerGame("", DeckType.FIBONACCI, false))
            }
    }

    fun onPlayersInGameChange(): Observable<Pair<List<Player>, DiffUtil.DiffResult>> {
        val emptyList: List<Player> = ArrayList()
        val initialPair: Pair<List<Player>, DiffUtil.DiffResult> = Pair.create(emptyList, null)

        return socketSubject.filter { event ->
                event.type == JOIN_LEAVE_EVENT
            }
            .switchMap { event ->
                Observable.just((event as JoinLeaveEvent).data)
            }
            .scan(initialPair, { pair, next ->
                val callback = PlayerDiffCallback(pair.first!!, next)
                val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(callback)
                Pair.create(next, result)
            })
            .skip(1)
    }

    fun onActiveCardSetChange(): Observable<Pair<List<ActiveCard>, DiffUtil.DiffResult>> {
        val emptyList: List<ActiveCard> = ArrayList()
        val initialPair: Pair<List<ActiveCard>, DiffUtil.DiffResult> = Pair.create(emptyList, null)

        return socketSubject.filter { event ->
                event.type == CARD_SUBMIT_EVENT
            }
            .switchMap { event ->
                Observable.just((event as CardSubmitEvent).data)
            }
            .scan(initialPair, { pair, next ->
                val callback = ActiveCardDiffCallback(pair.first!!, next)
                val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(callback)
                Pair.create(next, result)
            })
            .skip(1)
    }

    private fun initializeSocketListeners() {
        socket.on(START_GAME_EVENT, { args ->
            val jsonString = args[0] as String
            val event = gson.fromJson(jsonString, StartGameEvent::class.java)
            socketSubject.onNext(event)
        })

        socket.on(JOIN_LEAVE_EVENT, { args ->
            val jsonString = args[0] as String
            val event = gson.fromJson(jsonString, JoinLeaveEvent::class.java)
            socketSubject.onNext(event)
        })

        socket.on(CARD_SUBMIT_EVENT, { args ->
            val jsonString = args[0] as String
            val event = gson.fromJson(jsonString, CardSubmitEvent::class.java)
            socketSubject.onNext(event)
        })

        socket.on(END_GAME_EVENT, { args ->
            val jsonString = args[0] as String
            val event = gson.fromJson(jsonString, EndGameEvent::class.java)
            socketSubject.onNext(event)
        })
    }

    companion object {
        const val JOIN_LEAVE_EVENT = "join-leave-game"
        const val CARD_SUBMIT_EVENT = "card-submit"
        const val START_GAME_EVENT = "start-game"
        const val END_GAME_EVENT = "end-game"
    }
}