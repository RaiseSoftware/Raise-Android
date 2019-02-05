package com.cameronvwilliams.raise.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.transition.ChangeBounds
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.core.app.SharedElementCallback
import androidx.core.content.ContextCompat.startActivity
import android.text.Editable
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import com.cameronvwilliams.raise.BuildConfig
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.data.model.Story
import com.cameronvwilliams.raise.ui.intro.create.CreateFragment
import com.cameronvwilliams.raise.ui.intro.create.CreatePasscodeFragment
import com.cameronvwilliams.raise.ui.offline.view.OfflineFragment
import com.cameronvwilliams.raise.ui.intro.views.*
import com.cameronvwilliams.raise.ui.offline.view.OfflineGameFragment
import com.cameronvwilliams.raise.ui.offline.view.OfflineSettingsFragment
import com.cameronvwilliams.raise.ui.pending.PendingActivity
import com.cameronvwilliams.raise.ui.pending.views.CreateStoryFragment
import com.cameronvwilliams.raise.ui.pending.views.PendingFragment
import com.cameronvwilliams.raise.ui.poker.PokerActivity
import com.cameronvwilliams.raise.ui.poker.views.PokerFragment
import com.cameronvwilliams.raise.ui.scanner.ScannerActivity
import com.cameronvwilliams.raise.ui.scanner.views.ScannerFragment
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class Navigator(private val fm: FragmentManager, val context: Context) {

    private val scannerRequestCode = 1000
    private val scannerRequestObservable = BehaviorSubject.createDefault<Result<PokerGame>>(Result(ResultEnum.INITIAL, null))

    fun goBack() {
        fm.popBackStack()
    }

    fun goToIntro() {
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

    fun goToJoinGame(
        joinCardView: View,
        fillFormText: View,
        joinForm: View,
        userNameEditText: View,
        formDivider: View,
        gameIdEditText: View,
        orDividerText: View,
        barcodeText: View
    ) {

        val joinFragment = JoinFragment.newInstance()

        val t = ChangeBounds()
        t.duration = 300L
        t.interpolator = AccelerateDecelerateInterpolator()

        joinFragment.enterTransition = t
        joinFragment.exitTransition = t
        joinFragment.sharedElementEnterTransition = Transition()
        joinFragment.sharedElementReturnTransition = Transition()

        fm.beginTransaction()
            .addSharedElement(joinCardView, "joinCardView")
            .addSharedElement(fillFormText, "fillFormText")
            .addSharedElement(joinForm, "joinForm")
            .addSharedElement(userNameEditText, "userNameEditText")
            .addSharedElement(formDivider, "formDivider")
            .addSharedElement(gameIdEditText, "gameIdEditText")
            .addSharedElement(orDividerText, "orDividerText")
            .addSharedElement(barcodeText, "barcodeText")
            .replace(R.id.layoutRoot, joinFragment)
            .addToBackStack(null)
            .commit()
    }

    fun goToOffline(offlineCardView: View, selectDeckText: View, fibonacciRadio: View, tshirtRadio: View) {

        val offlineFragment = OfflineFragment.newInstance()

        val t = ChangeBounds()
        t.duration = 300L
        t.interpolator = AccelerateDecelerateInterpolator()

        offlineFragment.enterTransition = t
        offlineFragment.exitTransition = t
        offlineFragment.sharedElementEnterTransition = Transition()
        offlineFragment.sharedElementReturnTransition = Transition()

        fm.beginTransaction()
            .addSharedElement(offlineCardView, "offlineCardView")
            .addSharedElement(selectDeckText, "selectDeckText")
            .addSharedElement(fibonacciRadio, "fibonacciRadio")
            .addSharedElement(tshirtRadio, "tshirtRadio")
            .replace(R.id.layoutRoot, offlineFragment)
            .addToBackStack(null)
            .commit()
    }

    fun goToCreateGame(
        selectDeckText: View,
        fibonacciRadio: View,
        tshirtRadio: View,
        joinForm: View,
        userNameEditText: View,
        formDivider: View,
        gameNameText: View,
        createCardView: View
    ) {
        val createFragment = CreateFragment.newInstance()

        val t = ChangeBounds()
        t.duration = 300L

        createFragment.enterTransition = t
        createFragment.exitTransition = t

        createFragment.sharedElementEnterTransition = Transition()
        createFragment.sharedElementReturnTransition = Transition()
        createFragment.setEnterSharedElementCallback(object : SharedElementCallback() {

            val state: MutableMap<String, Any> = mutableMapOf()
            var opened = false

            override fun onSharedElementEnd(
                sharedElementNames: MutableList<String>?,
                sharedElements: MutableList<View>?,
                sharedElementSnapshots: MutableList<View>?
            ) {
                sharedElementNames?.forEachIndexed { index, name ->
                    when (name) {
                        "fibonacciRadio" -> {
                            (sharedElements?.get(index) as RadioButton).isChecked = state[name] as Boolean
                            (sharedElements[index] as RadioButton).jumpDrawablesToCurrentState()
                        }
                        "requirePasscodeCheckbox" -> {
                            (sharedElements?.get(index) as CheckBox).isChecked = state[name] as Boolean
                            (sharedElements[index] as CheckBox).jumpDrawablesToCurrentState()
                        }
                        "tshirtRadio" -> {
                            (sharedElements?.get(index) as RadioButton).isChecked = state[name] as Boolean
                            (sharedElements[index] as RadioButton).jumpDrawablesToCurrentState()
                        }
                        "userNameEditText" -> {
                            (sharedElements?.get(index) as EditText).text = state[name] as Editable
                            (sharedElements[index] as EditText).isFocusableInTouchMode = true
                        }
                        "gameNameText" -> {
                            (sharedElements?.get(index) as EditText).text = state[name] as Editable
                        }

                    }
                }

                opened = true
            }

            override fun onSharedElementStart(
                sharedElementNames: MutableList<String>?,
                sharedElements: MutableList<View>?,
                sharedElementSnapshots: MutableList<View>?
            ) {
                sharedElementNames?.forEachIndexed { index, name ->
                    when (name) {
                        "fibonacciRadio" -> state[name] = (sharedElements?.get(index) as RadioButton).isChecked
                        "requirePasscodeCheckbox" -> state[name] = (sharedElements?.get(index) as CheckBox).isChecked
                        "tshirtRadio" -> state[name] = if (opened) (sharedElements?.get(index) as RadioButton).isChecked else false
                        "userNameEditText" -> {
                            state[name] = (sharedElements?.get(index) as EditText).text
                            state["$name-focus"] = (sharedElements[index] as EditText).hasFocus()
                        }
                        "gameNameText" -> state[name] = (sharedElements?.get(index) as EditText).text

                    }
                }
            }
        })

        fm.beginTransaction()
            .addSharedElement(selectDeckText, "qselectDeckText")
            .addSharedElement(fibonacciRadio, "qfibonacciRadio")
            .addSharedElement(tshirtRadio, "qtshirtRadio")
            .addSharedElement(joinForm, "qjoinForm")
            .addSharedElement(userNameEditText, "quserNameEditText")
            .addSharedElement(formDivider, "qformDivider")
            .addSharedElement(gameNameText, "qgameNameText")
            .addSharedElement(createCardView, "qcreateCardView")
            .replace(R.id.layoutRoot, createFragment)
            .addToBackStack(null)
            .commit()
    }

    fun goToCreatePasscode(game: PokerGame, player: Player) {
        val fragment = CreatePasscodeFragment.newInstance(game, player)

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

    fun goToPasscode(gameName: String, player: Player) {
        val fragment = PasscodeFragment.newInstance()
        val bundle = Bundle()

        with(PasscodeFragment.BundleOptions) {
            bundle.setGameName(gameName)
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

    fun goToPrivacyPolicy() {
        val fragment: HtmlFragment = with(HtmlFragment) {
             newInstance(EXTRA_PRIVACY_POLICY)
        }

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

    fun goToTerms() {
        val fragment = with(HtmlFragment) {
            newInstance(EXTRA_TERMS)
        }

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

    fun goToPlayStore() {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)))
        } catch (e: android.content.ActivityNotFoundException) {
            Timber.e(e)
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)
                )
            )
        }
    }

    fun goToGithubIssues() {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/RaiseSoftware/Raise-Android/issues")))
    }

    fun goToPendingView(pokerGame: PokerGame, userName: String, moderatorMode: Boolean) {
        val intent = Intent(context, PendingActivity::class.java)

        (context as MainActivity).recreate()

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

    fun showCreateStory(pokerGame: PokerGame, cb: (MutableList<Story>) -> Unit) {
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

    fun goToOffline(deckType: DeckType) {
        val fragment = OfflineGameFragment.newInstance(deckType)

        fm.beginTransaction()
            .replace(R.id.layoutRoot, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun goToOfflineSettings() {
        OfflineSettingsFragment.newInstance().show(fm, null)
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

    inner class Transition : TransitionSet() {
        init {
            ordering = ORDERING_TOGETHER
            addTransition(ChangeBounds())
            addTransition(ChangeTransform())
            duration = 300L
        }
    }

    data class Result<T>(val status: ResultEnum, val data: T?)

    enum class ResultEnum {
        SUCCESS,
        FAILURE,
        CANCELLED,
        INITIAL
    }
}