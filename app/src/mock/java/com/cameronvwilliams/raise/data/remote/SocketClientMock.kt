package com.cameronvwilliams.raise.data.remote


import androidx.recyclerview.widget.DiffUtil
import com.cameronvwilliams.raise.data.model.ActiveCard
import com.cameronvwilliams.raise.data.model.Card
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.Story
import com.cameronvwilliams.raise.data.model.event.SocketEvent
import com.cameronvwilliams.raise.util.ActiveCardDiffCallback
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class SocketClientMock: SocketAPI {
    override fun onNextUserStory(): Flowable<Story> {

    }

    override fun connect(token: String) {

    }

    override fun disconnect() {

    }

    override fun sendStartGameMessage() {

    }

    override fun sendSubmitCardMessage(card: Card) {

    }

    override fun sendEndGameMessage() {

    }

    override fun onGameStart(): Completable {
        return Completable.complete()
    }

    override fun onGameEnd(): Completable {
        return Completable.complete().delay(15, TimeUnit.SECONDS)
    }

    override fun onPlayersInGameChange(): Flowable<androidx.core.util.Pair<List<Player>, DiffUtil.DiffResult>> {
        val callback = ActiveCardDiffCallback(mutableListOf(), mutableListOf())
        val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(callback, false)
        return Flowable.just(Pair(arrayListOf(), result))
    }

    override fun onActiveCardSetChange(): Flowable<androidx.core.util.Pair<List<ActiveCard>, DiffUtil.DiffResult>> {
        val callback = ActiveCardDiffCallback(mutableListOf(), mutableListOf())
        val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(callback, false)
        return Flowable.just(Pair(arrayListOf(), result))
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