package com.cameronvwilliams.raise.data.model.event

import com.cameronvwilliams.raise.data.model.ActiveCard
import com.cameronvwilliams.raise.data.remote.SocketClient

class CardSubmitEvent(val data: List<ActiveCard>): SocketEvent(SocketClient.CARD_SUBMIT_EVENT)