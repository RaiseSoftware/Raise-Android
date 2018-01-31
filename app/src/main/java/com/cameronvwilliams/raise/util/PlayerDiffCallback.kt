package com.cameronvwilliams.raise.util

import android.support.v7.util.DiffUtil
import com.cameronvwilliams.raise.data.model.Player

class PlayerDiffCallback(private val current: List<Player>, private val next: List<Player>) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val currentItem = current[oldItemPosition]
        val nextItem = next[newItemPosition]
        return currentItem.userName == nextItem.userName
    }

    override fun getOldListSize() = current.size

    override fun getNewListSize() = next.size


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val currentItem = current[oldItemPosition]
        val nextItem = next[newItemPosition]
        return currentItem == nextItem
    }
}