package com.cameronvwilliams.raise.data.model

import android.os.Parcel
import com.cameronvwilliams.raise.util.Parcelable
import com.cameronvwilliams.raise.util.parcelableCreator

data class Player(
    val uid: String? = "",
    val name: String? = "",
    val roles: ArrayList<Role> = arrayListOf()
) : Parcelable {

    private constructor(parcel: Parcel) : this(
        uid = parcel.readString(),
        name = parcel.readString(),
        roles = parcel.createTypedArrayList(Role.CREATOR)
    )

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeTypedList(roles)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::Player)
    }
}