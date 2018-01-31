package com.cameronvwilliams.raise.data.model.event

import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.remote.SocketClient

class JoinLeaveEvent(val data: List<Player>): SocketEvent(SocketClient.JOIN_LEAVE_EVENT)