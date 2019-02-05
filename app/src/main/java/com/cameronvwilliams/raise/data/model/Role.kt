package com.cameronvwilliams.raise.data.model

import android.os.Parcel
import com.cameronvwilliams.raise.util.Parcelable
import com.cameronvwilliams.raise.util.parcelableCreator

enum class Role: Parcelable {
    MODERATOR,
    PLAYER;

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(ordinal)
    }


    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator {
            Role.values()[it.readInt()]
        }
    }
}
