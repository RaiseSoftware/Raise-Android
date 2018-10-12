package com.cameronvwilliams.raise.data.model

import android.os.Parcel
import com.cameronvwilliams.raise.util.*
import java.util.*

data class PokerGame(
    val gameName: String? = "",
    val deckType: DeckType? = null,
    var requiresPasscode: Boolean = false,
    val gameId: String? = "",
    var qrcode: String? = "",
    var passcode: String? = "",
    val gameUuid: String? = "",
    val createdDateTime: Date? = null,
    val startDateTime: Date? = null,
    val players: MutableList<Player>? = mutableListOf()
) : Parcelable {

    private constructor(parcel: Parcel) : this(
        gameName = parcel.readString(),
        deckType = parcel.readEnum<DeckType>()!!,
        requiresPasscode = parcel.readBoolean(),
        gameId = parcel.readString(),
        qrcode = parcel.readString(),
        passcode = parcel.readString(),
        gameUuid = parcel.readString(),
        createdDateTime = parcel.readDate(),
        startDateTime = parcel.readDate(),
        players = ArrayList<Player>().apply {
            parcel.readList(this, Player::class.java.classLoader)
        }
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(gameName)
        writeEnum(deckType)
        writeBoolean(requiresPasscode)
        writeString(gameId)
        writeString(qrcode)
        writeString(passcode)
        writeString(gameUuid)
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