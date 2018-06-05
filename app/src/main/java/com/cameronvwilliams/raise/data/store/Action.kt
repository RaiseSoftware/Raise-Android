package com.cameronvwilliams.raise.data.store

class Action<T : Enum<*>, V>(val type: T, val value: V? = null) {

    override fun toString(): String {
        return if (this.value != null) {
            this.type.toString() + ": " + this.value.toString()
        } else {
            this.type.toString()
        }
    }
}
