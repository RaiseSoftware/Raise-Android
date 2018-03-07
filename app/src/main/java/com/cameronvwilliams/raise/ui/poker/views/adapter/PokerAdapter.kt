package com.cameronvwilliams.raise.ui.poker.views.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.poker.views.PlayerCardFragment
import com.cameronvwilliams.raise.ui.poker.views.PokerCardFragment

class PokerAdapter(fm: FragmentManager, pokerGame: PokerGame) : FragmentPagerAdapter(fm) {

    private var fragments: Array<Fragment>

    init {
        val pokerCardFragment = PokerCardFragment.newInstance()
        val bundle = Bundle()

        with(PokerCardFragment.BundleOptions) {
            bundle.setPokerGame(pokerGame)
        }

        pokerCardFragment.arguments = bundle

        fragments = arrayOf(
            pokerCardFragment,
            PlayerCardFragment.newInstance()
        )
    }

    override fun getItem(position: Int) = fragments[position]

    override fun getCount(): Int = fragments.size

}