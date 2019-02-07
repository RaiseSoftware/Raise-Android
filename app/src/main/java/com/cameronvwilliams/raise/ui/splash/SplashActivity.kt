package com.cameronvwilliams.raise.ui.splash

import android.content.Intent
import android.os.Bundle
import com.cameronvwilliams.raise.data.DataManager
import com.cameronvwilliams.raise.ui.MainActivity
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import timber.log.Timber
import javax.inject.Inject


class SplashActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var dm: DataManager

    private lateinit var subscription: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscription = dm.signIn()
            .subscribe({ loggedIn ->
                if (loggedIn) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // TODO Find elegant solution
                }
            }, { t ->
                Timber.e(t)
                throw OnErrorNotImplementedException(t)
            })
    }

    override fun onDestroy() {
        subscription.dispose()
        super.onDestroy()
    }
}
