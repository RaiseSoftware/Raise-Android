package com.cameronvwilliams.raise.data.remote

import android.support.v4.util.Pair
import android.support.v7.util.DiffUtil
import com.cameronvwilliams.raise.data.model.ActiveCard
import com.cameronvwilliams.raise.data.model.Card
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.event.SocketEvent
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class SocketClientMock: SocketAPI {
    override fun connect(token: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disconnect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendStartGameMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendSubmitCardMessage(card: Card) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendEndGameMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGameStart(): Observable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGameEnd(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPlayersInGameChange(): Observable<Pair<List<Player>, DiffUtil.DiffResult>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActiveCardSetChange(): Observable<Pair<List<ActiveCard>, DiffUtil.DiffResult>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val joinLeaveSubject: BehaviorSubject<SocketEvent> = BehaviorSubject.create()
    private val cardSubmitSubject: BehaviorSubject<SocketEvent> = BehaviorSubject.create()
    private val startGameSubject: BehaviorSubject<SocketEvent> = BehaviorSubject.create()
    private val endGameSubject: BehaviorSubject<SocketEvent> = BehaviorSubject.create()

    init {
        joinLeaveSubject.replay(1).refCount()
        cardSubmitSubject.replay(1).refCount()
        startGameSubject.replay(1).refCount()
        endGameSubject.replay(1).refCount()
    }


}