package com.cameronvwilliams.raise.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.intro.views.*
import com.cameronvwilliams.raise.ui.pending.PendingActivity
import com.cameronvwilliams.raise.ui.pending.views.PendingFragment
import com.cameronvwilliams.raise.ui.poker.PokerActivity
import com.cameronvwilliams.raise.ui.poker.views.PokerFragment

class Navigator(private val fm: FragmentManager, val context: Context) {

    // I decided not to include this in the article because this is more like a corner case, but I think I have quite clean solution for that. To implement this, Navigator should recieve a list of requestCodes in the constructor, one for each “start for result” action he can do. This list is provided by somebody who can receive onActivityResult(). In onActivityResult() you map requestCode to the proper Navigator and pass Intent to it. Navigator then unwrap Intent and pass pure data to the Presenter. So still all navigation related stuff will be in the Navigator.

    fun goBack() {
        fm.popBackStackImmediate()
    }

    fun goToIntro(animate: Boolean = true) {
        if (!fm.popBackStackImmediate("intro", 0)) {
            fm.beginTransaction()
                .replace(R.id.layoutRoot, IntroFragment.newInstance())
                .setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left,
                    R.anim.slide_out_right,
                    R.anim.slide_in_right
                )
                .addToBackStack("intro")
                .commit()
        }
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

    fun goToPendingView(pokerGame: PokerGame, userName: String) {
        val intent = Intent(context, PendingActivity::class.java)
        with(PendingActivity.IntentOptions) {
            intent.setPokerGame(pokerGame)
            intent.setUserName(userName)
        }
        context.startActivity(intent)
    }

    fun goToPending(pokerGame: PokerGame, userName: String) {
        val fragment = PendingFragment.newInstance()
        val bundle = Bundle()

        with(PendingFragment.BundleOptions) {
            bundle.setPokerGame(pokerGame)
            bundle.setUserName(userName)
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

    fun goToPokerGameView() {
        val intent = Intent(context, PokerActivity::class.java)
        context.startActivity(intent)
    }

    fun goToPoker() {
        fm.beginTransaction()
            .replace(R.id.layoutRoot, PokerFragment.newInstance())
            .commit()
    }
}