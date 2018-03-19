package com.cameronvwilliams.raise.ui.pending.views.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.Story
import com.cameronvwilliams.raise.ui.pending.views.viewholder.StoryListViewHolder

class StoryListAdapter(private var list: List<Story>) :
    RecyclerView.Adapter<StoryListViewHolder>() {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: StoryListViewHolder?, position: Int) {
        holder?.bindStory(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StoryListViewHolder {
        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.pending_story_row_item, parent, false)

        return StoryListViewHolder(view)
    }

    fun updateStoryList(stories: List<Story>) {
        list = stories
        notifyDataSetChanged()
    }
}
