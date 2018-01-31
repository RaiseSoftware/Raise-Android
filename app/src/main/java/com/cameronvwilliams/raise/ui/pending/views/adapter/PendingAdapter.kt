package com.cameronvwilliams.raise.ui.pending.views.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.pending.views.PlayerListFragment
import com.cameronvwilliams.raise.ui.pending.views.PokerInfoFragment

class PendingAdapter(fm: FragmentManager, game: PokerGame) : FragmentPagerAdapter(fm) {

    private val fragments = arrayOf(PokerInfoFragment.newInstance(game), PlayerListFragment.newInstance())

    override fun getItem(position: Int) = fragments[position]

    override fun getCount(): Int = fragments.size

}