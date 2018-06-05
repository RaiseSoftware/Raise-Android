package com.cameronvwilliams.raise.data.remote

import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.data.model.Story
import com.cameronvwilliams.raise.data.model.Token
import com.cameronvwilliams.raise.data.model.api.PokerGameBody
import com.cameronvwilliams.raise.data.model.api.PokerGameResponse
import com.cameronvwilliams.raise.data.model.api.StoryBody
import io.reactivex.Single

class RaiseAPIMock: RaiseAPI {
    override fun findPokerGame(gameId: String, name: String, passcode: String?): Single<PokerGameResponse> {
        return Single.just(PokerGameResponse(PokerGame(deckType = DeckType.FIBONACCI, requiresPasscode = false, passcode = null), Token(1000,"")))
    }

    override fun createPokerGame(pokerGame: PokerGameBody): Single<PokerGameResponse> {
        return Single.just(PokerGameResponse(pokerGame.pokerGame, Token(1000,"")))
    }

    override fun createUserStory(story: StoryBody): Single<List<Story>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findUserStoriesForGame(gameUuid: String): Single<List<Story>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}