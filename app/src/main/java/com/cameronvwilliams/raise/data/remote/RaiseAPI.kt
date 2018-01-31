package com.cameronvwilliams.raise.data.remote

import com.cameronvwilliams.raise.data.model.PokerGame
import io.reactivex.Observable
import retrofit2.http.*

interface RaiseAPI {

    @GET("poker-game/{gameId}")
    fun findPokerGame(@Path("gameId") gameId: String, @Query("passcode") passcode: String?):
            Observable<PokerGame>

    @POST("poker-game")
    fun createPokerGame(@Body game: PokerGame): Observable<PokerGame>
}
