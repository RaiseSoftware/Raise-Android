package com.cameronvwilliams.raise.data.remote

import com.cameronvwilliams.raise.data.model.Story
import com.cameronvwilliams.raise.data.model.api.PokerGameBody
import com.cameronvwilliams.raise.data.model.api.PokerGameResponse
import com.cameronvwilliams.raise.data.model.api.StoryBody
import io.reactivex.Single
import retrofit2.http.*

interface RaiseAPI {

    @GET("poker-game/{gameId}")
    fun findPokerGame(@Path("gameId") gameId: String, @Query("name") name: String, @Query("passcode") passcode: String?):
            Single<PokerGameResponse>

    @POST("poker-game")
    fun createPokerGame(@Body pokerGame: PokerGameBody): Single<PokerGameResponse>

    @POST("user-story")
    fun createUserStory(@Body story: StoryBody): Single<MutableList<Story>>

    @GET("user-story")
    fun findUserStoriesForGame(@Query("gameUuid") gameUuid: String): Single<List<Story>>
}
