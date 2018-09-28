package com.cameronvwilliams.raise.ui.poker.views.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.poker.views.PlayerCardFragment
import com.cameronvwilliams.raise.ui.poker.views.PokerCardFragment

class PokerAdapter(fm: FragmentManager, pokerGame: PokerGame) : FragmentPagerAdapter(fm) {

    private var fragments: Array<Fragment>

    init {
        val pokerCardFragment = PokerCardFragment.newInstance()
        val bundle = Bundle()

        with(PokerCardFragment.BundleOptions) {
            bundle.setDeckType(pokerGame.deckType!!)
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