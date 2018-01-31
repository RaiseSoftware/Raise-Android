package com.cameronvwilliams.raise.ui.poker.views.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.cameronvwilliams.raise.ui.poker.views.PlayerCardFragment
import com.cameronvwilliams.raise.ui.poker.views.PokerCardFragment

class PokerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments =
        arrayOf(
            PokerCardFragment.newInstance(),
            PlayerCardFragment.newInstance()
        )

    override fun getItem(position: Int) = fragments[position]

    override fun getCount(): Int = fragments.size

}