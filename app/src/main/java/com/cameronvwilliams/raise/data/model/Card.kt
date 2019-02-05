package com.cameronvwilliams.raise.data.model

import android.os.Parcel
import com.cameronvwilliams.raise.util.*

data class Card(
    private val type: DeckType,
    val value: CardValue,
    var faceUp: Boolean? = null
) : Parcelable {

    private constructor(parcel: Parcel) : this(
        type = parcel.readEnum<DeckType>()!!,
        value = parcel.readEnum<CardValue>()!!,
        faceUp = parcel.readValue(Boolean::class.java.classLoader) as Boolean?
    )
    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeEnum(type)
        writeEnum(value)
        writeValue(faceUp)
    }

    fun flip() {
        faceUp?.let { currentValue ->
            faceUp = !currentValue
        }
    }

    fun flip(faceUp: Boolean) {
        this.faceUp = faceUp
    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::Card)
    }
}
