package com.cameronvwilliams.raise.data.model

import android.os.Parcel
import android.os.Parcelable
import com.cameronvwilliams.raise.util.parcelableCreator

data class Player(val name: String) : Parcelable {

    private constructor(parcel: Parcel) : this(
        name = parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::Player)
    }
}