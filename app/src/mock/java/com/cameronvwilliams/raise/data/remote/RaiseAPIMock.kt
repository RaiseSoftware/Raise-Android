package com.cameronvwilliams.raise.data.remote

import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.util.Base64
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
import java.util.*
import java.util.concurrent.TimeUnit
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream


class RaiseAPIMock(private val retrofit: Retrofit): RaiseAPI {

    private val rand = Random(10)

    override fun findPokerGame(gameId: String, name: String, passcode: String?): Single<PokerGameResponse> {

        val passcodeError = Response.error<String>(403,
            ResponseBody.create(MediaType.parse("application/json"), "{\"statusCode\":403}")
        )
        val notFoundError = Response.error<String>(403,
            ResponseBody.create(MediaType.parse("application/json"), "{\"statusCode\":404, \"message\": \"Test Failure\"}")
        )

        return when {
            gameId.take(1).toInt() < 4 -> Single.just(
                PokerGameResponse(PokerGame(deckType = DeckType.FIBONACCI, requiresPasscode = false, passcode = null), Token(1000,""))
            ).delay(rand.nextLong(), TimeUnit.SECONDS)
            gameId.take(1).toInt() < 7 -> Single.error<PokerGameResponse>(
                RetrofitException("", "", passcodeError, RetrofitException.Kind.HTTP, null, retrofit)
            ).delay(rand.nextLong(), TimeUnit.SECONDS, true)
            else -> Single.error<PokerGameResponse>(
                RetrofitException("", "", notFoundError, RetrofitException.Kind.HTTP, null, retrofit)
            ).delay(rand.nextLong(), TimeUnit.SECONDS, true)
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