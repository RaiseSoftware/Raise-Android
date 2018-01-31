package com.cameronvwilliams.raise.ui

interface BaseUserActions<T> {
    var actions: T

    fun onViewCreated(actions: T) {
        this.actions = actions
    }

    fun onViewDestroyed() {}

    fun onBackPressed(): Boolean {
        return false
    }
}