package com.cameronvwilliams.raise.util

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.cameronvwilliams.raise.data.DataManager

class AppLifeCycleObserver(val dm: DataManager) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        dm.leaveGame()
    }
}