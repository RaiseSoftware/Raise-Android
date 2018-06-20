package com.cameronvwilliams.raise.data.remote

import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.data.model.Story
import com.cameronvwilliams.raise.data.model.Token
import com.cameronvwilliams.raise.data.model.api.PokerGameBody
import com.cameronvwilliams.raise.data.model.api.PokerGameResponse
import com.cameronvwilliams.raise.data.model.api.StoryBody
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit

class RaiseAPIMock(private val retrofit: Retrofit): RaiseAPI {

    private val passcodeError = Response.error<String>(403, ResponseBody.create(MediaType.parse("application/json"), "{\"statusCode\":403}"))
    private val notFoundError = Response.error<String>(403, ResponseBody.create(MediaType.parse("application/json"), "{\"statusCode\":404}"))

    override fun findPokerGame(gameId: String, name: String, passcode: String?): Single<PokerGameResponse> {
        return when {
            gameId.take(1).toInt() < 4 -> Single.just(
                PokerGameResponse(PokerGame(deckType = DeckType.FIBONACCI, requiresPasscode = false, passcode = null), Token(1000,""))
            )
            gameId.take(1).toInt() < 7 -> Single.error(
                RetrofitException("", "", passcodeError, RetrofitException.Kind.HTTP, null, retrofit)
            )
            else -> Single.error(
                RetrofitException("", "", notFoundError, RetrofitException.Kind.HTTP, null, retrofit)
            )
        }
    }

    override fun createPokerGame(pokerGame: PokerGameBody): Single<PokerGameResponse> {
        return Single.just(PokerGameResponse(pokerGame.pokerGame, Token(1000,"")))
    }

    override fun createUserStory(story: StoryBody): Single<MutableList<Story>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findUserStoriesForGame(gameUuid: String): Single<List<Story>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}