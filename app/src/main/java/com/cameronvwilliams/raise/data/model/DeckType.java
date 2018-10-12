package com.cameronvwilliams.raise.data.model;

import com.google.gson.annotations.SerializedName;

public enum DeckType {
    @SerializedName("fibonacci")
    FIBONACCI,
    @SerializedName("t-shirt")
    T_SHIRT,
    NONE
}