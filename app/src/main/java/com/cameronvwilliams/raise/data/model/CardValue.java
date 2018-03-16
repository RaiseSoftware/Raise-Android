package com.cameronvwilliams.raise.data.model;

import com.google.gson.annotations.SerializedName;

public enum CardValue {
    // T-Shirt Sizes
    @SerializedName("XS")
    X_SMALL,
    @SerializedName("S")
    SMALL,
    @SerializedName("M")
    MEDIUM,
    @SerializedName("L")
    LARGE,
    @SerializedName("XL")
    X_LARGE,

    // Fibonacci
    @SerializedName("0")
    ZERO,
    @SerializedName("1/2")
    ONE_HALF,
    @SerializedName("1")
    ONE,
    @SerializedName("2")
    TWO,
    @SerializedName("3")
    THREE,
    @SerializedName("5")
    FIVE,
    @SerializedName("8")
    EIGHT,
    @SerializedName("13")
    THIRTEEN,
    @SerializedName("20")
    TWENTY,
    @SerializedName("40")
    FORTY,
    @SerializedName("100")
    ONE_HUNDRED,
    @SerializedName("infinity")
    INFINITY,

    // Other
    @SerializedName("?")
    QUESTION_MARK,
    @SerializedName("coffee")
    COFFEE
}