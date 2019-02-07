package com.cameronvwilliams.raise.ui.pending.views.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cameronvwilliams.raise.data.model.Story
import kotlinx.android.synthetic.main.pending_story_row_item.view.*

class StoryListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindStory(story: Story) {
        itemView.title.text = story.title
    }
}