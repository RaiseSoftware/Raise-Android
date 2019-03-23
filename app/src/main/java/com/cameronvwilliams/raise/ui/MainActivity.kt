package com.cameronvwilliams.raise.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.cameronvwilliams.raise.BuildConfig
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.util.deeplink.AppDeepLink
import com.cameronvwilliams.raise.util.deeplink.WebDeepLink
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

open class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_activity)

        AppCenter.start(application, BuildConfig.APP_CENTER_KEY,
            Distribute::class.java, Analytics::class.java, Crashes::class.java)

        savedInstanceState?.let {} ?: navigator.goToIntro()

        intent.getStringExtra("gameId")?.let {
            //navigator.goToJoinGame()
        }
    }

    fun enterFullScreen() {
        window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    }

    fun exitFullScreen() {
        window.decorView.systemUiVisibility = 0
    }

    fun setOfflineMode() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.tall_blue)
    }

    override fun onBackPressed() {
        val fragmentList = supportFragmentManager.fragments.reversed()
        var backPressed = false
        for (fragment in fragmentList) {
            backPressed = (fragment as BaseFragment).onBackPressed()

            if (backPressed) {
                break
            }
        }

        if (!backPressed) {
            super.onBackPressed()
        }
    }

    object DeeplinkIntents {
        @JvmStatic
        @WebDeepLink("invite-link")
        @AppDeepLink("invite-link")
        fun defaultIntent(context: Context, deepLinkData: Bundle): Intent {
            val intent = Intent(context, MainActivity::class.java)
            val gameId: String? = deepLinkData.getString("gameId")
            intent.putExtra("gameId", gameId)
            return Intent(context, MainActivity::class.java)
        }
    }
}
