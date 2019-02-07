package com.cameronvwilliams.raise.ui.pending.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.Story
import com.cameronvwilliams.raise.ui.pending.views.viewholder.StoryListViewHolder
import com.cameronvwilliams.raise.util.swap

class StoryListAdapter(private var list: MutableList<Story>) : RecyclerView.Adapter<StoryListViewHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: StoryListViewHolder, position: Int) {
        holder.bindStory(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pending_story_row_item, parent, false)

        return StoryListViewHolder(view)
    }

    fun onItemDismiss(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                list.swap(i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                list.swap(i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    fun onItemAdded(story: Story) {
        list.add(story)
        notifyItemInserted(list.lastIndex)
    }

    class SimpleItemTouchHelperCallback(val adapter: StoryListAdapter): ItemTouchHelper.Callback() {

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true
        }

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean {
            adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
            adapter.onItemDismiss(viewHolder.adapterPosition)
        }

    }
}
