package com.cameronvwilliams.raise

import androidx.lifecycle.ProcessLifecycleOwner
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.di.DaggerRaiseComponent
import com.cameronvwilliams.raise.di.RaiseComponent
import com.cameronvwilliams.raise.util.Analytics
import com.cameronvwilliams.raise.util.AppLifeCycleObserver
import com.evernote.android.state.StateSaver
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject


open class RaiseApplication : DaggerApplication() {

    @Inject
    lateinit var dm: DataManager

    private lateinit var raiseComponent: RaiseComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        raiseComponent = DaggerRaiseComponent.builder().application(this).build()
        return raiseComponent
    }

    override fun onCreate() {
        super.onCreate()

        Analytics.initialize(this)
        StateSaver.setEnabledForAllActivitiesAndSupportFragments(this, true)

        val lifeCycleObserver = AppLifeCycleObserver(dm)
        ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(lifeCycleObserver)
    }
}
