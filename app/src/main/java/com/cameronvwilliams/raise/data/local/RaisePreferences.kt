package com.cameronvwilliams.raise.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RaisePreferences @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private val tokenKey: String = "GAME_TOKEN_KEY"

    fun getCurrentGameToken(): String {
        return sharedPreferences.getString(tokenKey, "")
    }

    fun setCurrentGameToken(token: String) {
        setValue(token)
    }

    private fun setValue(value: String) {
        sharedPreferences.edit().putString(tokenKey, value).apply()
    }
}