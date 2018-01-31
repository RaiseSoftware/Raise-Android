package com.cameronvwilliams.analytics

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.google.firebase.FirebaseException
import com.google.firebase.analytics.FirebaseAnalytics

class Analytics {

    companion object {
        private lateinit var firebaseAnalytics: FirebaseAnalytics

        fun initialize(context: Context) {
            firebaseAnalytics = FirebaseAnalytics.getInstance(context)
        }

        fun trackEvent(eventName: String) {
            firebaseAnalytics.logEvent(eventName, Bundle())
        }

        fun trackScreen(activity: Activity, screenName: String) {
            firebaseAnalytics.setCurrentScreen(activity, screenName, null)
        }
    }
}