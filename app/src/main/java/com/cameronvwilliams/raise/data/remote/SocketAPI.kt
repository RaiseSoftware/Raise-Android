package com.cameronvwilliams.raise.data.remote

import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import com.cameronvwilliams.raise.data.model.ActiveCard
import com.cameronvwilliams.raise.data.model.Card
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.Story
import io.reactivex.Completable
import io.reactivex.Flowable

interface SocketAPI {
    fun connect(token: String)

    fun disconnect()

    fun sendStartGameMessage()

    fun sendSubmitCardMessage(card: Card)

    fun sendEndGameMessage()

    fun onGameStart(): Completable

    fun onGameEnd(): Completable

    fun onNextUserStory(): Flowable<Story>

    fun onPlayersInGameChange(): Flowable<Pair<List<Player>, DiffUtil.DiffResult>>

    fun onActiveCardSetChange(): Flowable<Pair<List<ActiveCard>, DiffUtil.DiffResult>>
}