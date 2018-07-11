package com.cameronvwilliams.raise.util

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.cameronvwilliams.raise.data.DataManager

class AppLifeCycleObserver(val dm: DataManager) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        dm.leaveGame()
    }
}