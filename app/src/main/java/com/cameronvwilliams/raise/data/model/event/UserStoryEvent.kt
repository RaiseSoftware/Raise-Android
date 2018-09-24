package com.cameronvwilliams.raise.data.model.event

import com.cameronvwilliams.raise.data.model.Story
import com.cameronvwilliams.raise.data.remote.SocketClient

class UserStoryEvent(val data: Story) : SocketEvent(SocketClient.USER_STORY_EVENT)