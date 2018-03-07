package com.cameronvwilliams.raise.data.model

import android.os.Parcel
import com.cameronvwilliams.raise.util.Parcelable
import com.cameronvwilliams.raise.util.parcelableCreator
import com.cameronvwilliams.raise.util.readBoolean
import com.cameronvwilliams.raise.util.writeBoolean

data class PokerGame(
    val gameName: String?,
    val deckType: DeckType?,
    val requiresPasscode: Boolean,
    val gameId: String? = "",
    val qrcode: String? = "",
    val passcode: String? = ""
) : Parcelable {

    private constructor(parcel: Parcel) : this(
        gameName = parcel.readString(),
        deckType = DeckType.valueOf(parcel.readString()),
        requiresPasscode = parcel.readBoolean(),
        gameId = parcel.readString(),
        qrcode = parcel.readString(),
        passcode = parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(gameName)
        writeString(deckType?.name)
        writeBoolean(requiresPasscode)
        writeString(gameId)
        writeString(qrcode)
        writeString(passcode)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::PokerGame)
    }
}