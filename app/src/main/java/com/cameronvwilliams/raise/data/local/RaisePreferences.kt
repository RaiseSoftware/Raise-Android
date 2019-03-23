package com.cameronvwilliams.raise.data.local

import android.content.SharedPreferences
import com.cameronvwilliams.raise.data.model.DeckType
import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


const val TOKEN_KEY: String = "GAME_TOKEN_KEY"
const val OFFLINE_DECK_TYPE = "OFFLINE_DECK_TYPE"

@Singleton
class RaisePreferences @Inject constructor(private val sharedPreferences: SharedPreferences) {
    private val rxPreferences = RxSharedPreferences.create(sharedPreferences)

    fun getCurrentGameToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, "")
    }

    fun setCurrentGameToken(token: String) {
        setValue(TOKEN_KEY, token)
    }

    fun getDeckType(): DeckType =
        DeckType.values()[sharedPreferences.getInt(OFFLINE_DECK_TYPE, DeckType.NONE.ordinal)]

    fun getDeckTypeObservable(): Observable<DeckType> {
        return rxPreferences.getInteger(OFFLINE_DECK_TYPE, DeckType.NONE.ordinal)
            .asObservable()
            .map { ordinal ->
                DeckType.values()[ordinal]
            }
    }

    fun setDeckType(deckType: DeckType) {
        setValue(OFFLINE_DECK_TYPE, deckType)
    }

    private fun setValue(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    private fun <T : Enum<T>> setValue(key: String, enum: T) {
        sharedPreferences.edit().putInt(key, enum.ordinal).apply()
    }
}