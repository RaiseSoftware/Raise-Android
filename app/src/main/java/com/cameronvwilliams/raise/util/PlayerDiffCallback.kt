package com.cameronvwilliams.raise.util

import androidx.recyclerview.widget.DiffUtil
import com.cameronvwilliams.raise.data.model.Player

class PlayerDiffCallback(private val current: List<Player>, private val next: List<Player>) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val currentItem = current[oldItemPosition]
        val nextItem = next[newItemPosition]
        return currentItem.name == nextItem.name
    }

    override fun getOldListSize() = current.size

    override fun getNewListSize() = next.size


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val currentItem = current[oldItemPosition]
        val nextItem = next[newItemPosition]
        return currentItem == nextItem
    }
}