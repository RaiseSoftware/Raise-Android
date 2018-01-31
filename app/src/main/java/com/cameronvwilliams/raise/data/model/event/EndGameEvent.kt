package com.cameronvwilliams.raise.data.model.event

import com.cameronvwilliams.raise.data.remote.SocketClient

class EndGameEvent : SocketEvent(SocketClient.END_GAME_EVENT)