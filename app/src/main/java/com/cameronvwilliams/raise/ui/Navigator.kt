package com.cameronvwilliams.raise.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
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
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.io.FileNotFoundException
import java.io.IOException
import java.io.Serializable

class Navigator(private val fm: FragmentManager, val context: Context) {

    private val imageRequestCode = 1000
    private val imageRequestObservable: PublishSubject<Bitmap> = PublishSubject.create()

    private val scannerRequestCode = 2000
    private val scannerRequestObservable: PublishSubject<PokerGame> = PublishSubject.create()

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == imageRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                var bitmap: Bitmap? = null
                try {
                    bitmap?.recycle()
                    val stream = context.contentResolver.openInputStream(data?.data)
                    bitmap = BitmapFactory.decodeStream(stream)
                    stream.close()
                } catch (e: FileNotFoundException) {
                    Timber.e(e)
                } catch (e: IOException) {
                    Timber.e(e)
                }

                imageRequestObservable.onNext(bitmap!!)
            }
        } else if (requestCode == scannerRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                val pokerGame: PokerGame = data?.extras!!["POKER_GAME"] as PokerGame

                scannerRequestObservable.onNext(pokerGame)
            }
        }
    }

    fun goBack() {
        fm.popBackStackImmediate()
    }

    fun goToIntro(animate: Boolean = true) {
        fm.beginTransaction()
            .replace(R.id.layoutRoot, IntroFragment.newInstance())
            .addToBackStack("intro")
            .commit()
    }

    fun goToSettings() {
        fm.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_out_right,
                R.anim.slide_in_right
            )
            .replace(R.id.layoutRoot, SettingsFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun goToJoinGame() {
        fm.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_out_right,
                R.anim.slide_in_right
            )
            .replace(R.id.layoutRoot, JoinFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun goToCreateGame() {
        fm.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_out_right,
                R.anim.slide_in_right,
                R.anim.slide_in_left,
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
                R.anim.slide_in_left, R.anim.slide_out_left,
                R.anim.slide_out_right, R.anim.slide_in_right
            )
            .replace(R.id.layoutRoot, fragment)
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
            .setCustomAnimations(
                R.anim.slide_in_left, R.anim.slide_out_left,
                R.anim.slide_out_right, R.anim.slide_in_right
            )
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

    fun showImageSelection(): Observable<Bitmap> {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(context as Activity, photoPickerIntent, imageRequestCode, null)
        return imageRequestObservable
    }

    fun goToScannerView(): Observable<PokerGame> {
        val intent = Intent(context, ScannerActivity::class.java)

        startActivityForResult(context as Activity, intent, scannerRequestCode, null)
        return scannerRequestObservable
    }

    fun goToScanner() {
        fm.beginTransaction()
            .replace(R.id.layoutRoot, ScannerFragment.newInstance())
            .commit()
    }
}