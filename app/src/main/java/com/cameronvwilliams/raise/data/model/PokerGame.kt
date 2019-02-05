package com.cameronvwilliams.raise.data.model

import android.os.Parcel
import com.cameronvwilliams.raise.util.*
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class PokerGame(
    @Exclude @set:Exclude @get:Exclude
    var uid: String? = "",
    val gameName: String? = "",
    val deckType: DeckType? = null,
    var qrcode: String? = "",
    var passcode: String? = "",
    val createdDateTime: Date? = null,
    val startDateTime: Date? = null,
    val players: MutableList<Player>? = mutableListOf()
) : Parcelable {

    private constructor(parcel: Parcel) : this(
        uid = parcel.readString(),
        gameName = parcel.readString(),
        deckType = parcel.readEnum<DeckType>()!!,
        qrcode = parcel.readString(),
        passcode = parcel.readString(),
        createdDateTime = parcel.readDate(),
        startDateTime = parcel.readDate(),
        players = ArrayList<Player>().apply {
            parcel.readList(this, Player::class.java.classLoader)
        }
    )

    fun withId(id: String): PokerGame {
        uid = id
        return this
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(uid)
        writeString(gameName)
        writeEnum(deckType)
        writeString(qrcode)
        writeString(passcode)
        writeDate(createdDateTime)
        writeDate(startDateTime)
        writeList(players)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::PokerGame)
    }
}