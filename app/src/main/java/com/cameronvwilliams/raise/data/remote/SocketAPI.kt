package com.cameronvwilliams.raise.data.remote

import android.support.v4.util.Pair
import android.support.v7.util.DiffUtil
import com.cameronvwilliams.raise.data.model.ActiveCard
import com.cameronvwilliams.raise.data.model.Card
import com.cameronvwilliams.raise.data.model.Player
import io.reactivex.Completable
import io.reactivex.Observable

interface SocketAPI {
    fun connect(token: String)

    fun disconnect()

    fun sendStartGameMessage()

    fun sendSubmitCardMessage(card: Card)

    fun sendEndGameMessage()

    fun onGameStart(): Observable<String>

    fun onGameEnd(): Completable

    fun onPlayersInGameChange(): Observable<android.support.v4.util.Pair<List<Player>, DiffUtil.DiffResult>>

    fun onActiveCardSetChange(): Observable<Pair<List<ActiveCard>, DiffUtil.DiffResult>>
}