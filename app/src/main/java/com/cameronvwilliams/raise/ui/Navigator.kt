package com.cameronvwilliams.raise.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat.startActivity
import com.cameronvwilliams.raise.BuildConfig
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.data.model.Story
import com.cameronvwilliams.raise.ui.intro.IntroActivity
import com.cameronvwilliams.raise.ui.intro.views.*
import com.cameronvwilliams.raise.ui.pending.PendingActivity
import com.cameronvwilliams.raise.ui.pending.views.CreateStoryFragment
import com.cameronvwilliams.raise.ui.pending.views.PendingFragment
import com.cameronvwilliams.raise.ui.poker.PokerActivity
import com.cameronvwilliams.raise.ui.poker.views.PokerFragment
import com.cameronvwilliams.raise.ui.scanner.ScannerActivity
import com.cameronvwilliams.raise.ui.scanner.views.ScannerFragment
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class Navigator(private val fm: FragmentManager, val context: Context) {

    private val scannerRequestCode = 1000
    private val scannerRequestObservable = BehaviorSubject.createDefault<Result<PokerGame>>(Result(ResultEnum.INITIAL, null))

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == scannerRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                val gameId = data?.extras?.getString("POKER_GAME_ID")
                val passcode = data?.extras?.getString("POKER_GAME_PASSCODE")

                scannerRequestObservable.onNext(Result(ResultEnum.SUCCESS, PokerGame(
                        gameId = gameId,
                        passcode = passcode,
                        requiresPasscode = passcode != null
                    )))
            } else {
                scannerRequestObservable.onNext(Result(ResultEnum.CANCELLED, null))
            }
        }
    }

    fun goBack() {
        fm.popBackStackImmediate()
    }

    fun goToIntro(animate: Boolean = true) {
        fm.beginTransaction()
            .replace(R.id.layoutRoot, IntroFragment.newInstance())
            .commit()
    }

    fun goToSettings() {
        fm.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.layoutRoot, SettingsFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun goToJoinGame() {
        fm.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.layoutRoot, JoinFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun goToCreateGame() {
        fm.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            .replace(R.id.layoutRoot, CreateFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun goToPasscode(gameId: String, player: Player) {
        val fragment = PasscodeFragment.newInstance()
        val bundle = Bundle()

        with(PasscodeFragment.BundleOptions) {
            bundle.setGameId(gameId)
            bundle.setPlayer(player)
        }

        fragment.arguments = bundle

        fm.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.layoutRoot, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun goToShareRaise() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.text_share_app, BuildConfig.APPLICATION_ID))
        sendIntent.type = "text/plain"
        startActivity(context, Intent.createChooser(sendIntent, context.getString(R.string.text_choose_app)), null)
    }

    fun goToAbout() {
        fm.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.layoutRoot, AboutFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun goToFeedback() {
        fm.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.layoutRoot, FeedbackFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun goToPendingView(pokerGame: PokerGame, userName: String, moderatorMode: Boolean) {
        val intent = Intent(context, PendingActivity::class.java)

        (context as IntroActivity).recreate()

        with(PendingActivity.IntentOptions) {
            intent.setPokerGame(pokerGame)
            intent.setUserName(userName)
            intent.setModeratorMode(moderatorMode)
        }
        context.startActivity(intent)
    }

    fun goToPending(pokerGame: PokerGame, userName: String, moderatorMode: Boolean) {
        val fragment = PendingFragment.newInstance()
        val bundle = Bundle()

        with(PendingFragment.BundleOptions) {
            bundle.setPokerGame(pokerGame)
            bundle.setUserName(userName)
            bundle.setModeratorMode(moderatorMode)
        }

        fragment.arguments = bundle

        fm.beginTransaction()
            .replace(R.id.layoutRoot, fragment)
            .commit()
    }

    fun showCreateStory(pokerGame: PokerGame, cb: (List<Story>) -> Unit) {
        fm.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .add(R.id.layoutRoot, CreateStoryFragment.newInstance(pokerGame, cb))
            .addToBackStack(null).commit()
    }

    fun goToPokerGameView(pokerGame: PokerGame) {
        val intent = Intent(context, PokerActivity::class.java)

        with(PokerActivity.IntentOptions) {
            intent.setPokerGame(pokerGame)
        }
        context.startActivity(intent)
    }

    fun goToPoker(pokerGame: PokerGame) {
        val fragment = PokerFragment.newInstance()
        val bundle = Bundle()

        with(PokerFragment.BundleOptions) {
            bundle.setPokerGame(pokerGame)
        }

        fragment.arguments = bundle

        fm.beginTransaction()
            .replace(R.id.layoutRoot, fragment)
            .commit()
    }

    fun goToScannerView() {
        val intent = Intent(context, ScannerActivity::class.java)
        startActivityForResult(context as Activity, intent, scannerRequestCode, null)
    }

    fun goToScanner() {
        fm.beginTransaction()
            .replace(R.id.layoutRoot, ScannerFragment.newInstance())
            .commit()
    }

    fun scannerResult(): Observable<Navigator.Result<PokerGame>> {
        return scannerRequestObservable
    }

    data class Result<T>(val status: ResultEnum, val data: T?)

    enum class ResultEnum {
        SUCCESS,
        FAILURE,
        CANCELLED,
        INITIAL
    }
}