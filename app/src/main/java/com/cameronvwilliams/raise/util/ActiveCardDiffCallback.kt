package com.cameronvwilliams.raise.util

import androidx.recyclerview.widget.DiffUtil
import com.cameronvwilliams.raise.data.model.ActiveCard

class ActiveCardDiffCallback(private val current: List<ActiveCard>, private val next: List<ActiveCard>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val currentItem = current[oldItemPosition]
        val nextItem = next[newItemPosition]
        return currentItem.player.name == nextItem.player.name
    }

    override fun getOldListSize() = current.size

    override fun getNewListSize() = next.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val currentItem = current[oldItemPosition]
        val nextItem = next[newItemPosition]
        return currentItem == nextItem
    }

}
