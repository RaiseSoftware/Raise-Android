package com.cameronvwilliams.raise.data.store

import com.cameronvwilliams.raise.data.model.PokerGame

abstract class State {

    internal abstract fun pokerGame(): PokerGame

}