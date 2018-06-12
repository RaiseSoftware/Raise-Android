package com.cameronvwilliams.raise.data.model

import android.os.Parcel
import com.cameronvwilliams.raise.util.*
import java.util.*

data class PokerGame(
    val gameName: String? = "",
    val deckType: DeckType? = null,
    val requiresPasscode: Boolean = false,
    val gameId: String? = "",
    val qrcode: String? = "",
    val passcode: String? = "",
    val gameUuid: String? = "",
    val createdDateTime: Date? = null
) : Parcelable {

    private constructor(parcel: Parcel) : this(
        gameName = parcel.readString(),
        deckType = parcel.readEnum<DeckType>()!!,
        requiresPasscode = parcel.readBoolean(),
        gameId = parcel.readString(),
        qrcode = parcel.readString(),
        passcode = parcel.readString(),
        gameUuid = parcel.readString(),
        createdDateTime = parcel.readDate()
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
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::PokerGame)
    }
}