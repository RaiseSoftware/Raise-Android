package com.cameronvwilliams.raise.ui.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.cameronvwilliams.raise.BuildConfig
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseActivity
import com.cameronvwilliams.raise.util.deeplink.AppDeepLink
import com.cameronvwilliams.raise.util.deeplink.WebDeepLink
import com.google.android.gms.ads.AdRequest
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import kotlinx.android.synthetic.main.intro_activity.*

open class IntroActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_activity)

        adView.loadAd(AdRequest.Builder().build())
        AppCenter.start(application, BuildConfig.APP_CENTER_KEY,
            Distribute::class.java, Analytics::class.java, Crashes::class.java)

        if (savedInstanceState == null) {
            navigator.goToIntro(false)
        }

        intent.getStringExtra("gameId")?.let {
            navigator.goToJoinGame()
        }
    }

    object DeeplinkIntents {
        @JvmStatic
        @WebDeepLink("invite-link")
        @AppDeepLink("invite-link")
        fun defaultIntent(context: Context, deepLinkData: Bundle): Intent {
            val intent = Intent(context, IntroActivity::class.java)
            val gameId: String? = deepLinkData.getString("gameId")
            intent.putExtra("gameId", gameId)
            return Intent(context, IntroActivity::class.java)
        }
    }
}
