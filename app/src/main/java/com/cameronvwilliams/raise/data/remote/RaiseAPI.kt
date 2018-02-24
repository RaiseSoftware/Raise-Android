package com.cameronvwilliams.raise.data.remote

import com.cameronvwilliams.raise.data.model.api.PokerGameBody
import com.cameronvwilliams.raise.data.model.api.PokerGameResponse
import io.reactivex.Observable
import retrofit2.http.*

interface RaiseAPI {

    @GET("poker-game/{gameId}")
    fun findPokerGame(@Path("gameId") gameId: String, @Query("name") name: String, @Query("passcode") passcode: String?):
            Observable<PokerGameResponse>

    @POST("poker-game")
    fun createPokerGame(@Body pokerGame: PokerGameBody): Observable<PokerGameResponse>
}
