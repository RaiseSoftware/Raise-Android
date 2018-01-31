package com.cameronvwilliams.raise.data.model.event

import com.cameronvwilliams.raise.data.remote.SocketClient

class StartGameEvent: SocketEvent(SocketClient.START_GAME_EVENT)
