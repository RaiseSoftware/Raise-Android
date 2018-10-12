package com.cameronvwilliams.raise.ui.offline.view


import android.content.Context
import android.os.Bundle
import androidx.annotation.ArrayRes
import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.deck_type_spinner_item.view.*
import kotlinx.android.synthetic.main.offline_settings_fragment.*

class OfflineSettingsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.offline_settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = getString(R.string.settings)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_white_24dp, null)
        val adapter = DeckTypeAdapter(requireContext(), R.layout.deck_type_spinner_item, R.array.text_deck_types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        deckTypeChoices.adapter = adapter
        deckTypeChoices.setSelection(1)
    }

    inner class DeckTypeAdapter(context: Context, @LayoutRes val resource: Int, @ArrayRes val textArrayResId: Int)
        : ArrayAdapter<String>(context, resource) {

        override fun getCount(): Int = context.resources.getStringArray(textArrayResId).size

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            convertView?.let {
                convertView.categoryText.text = "Select Deck Type"
                convertView.valueText.text = context.resources.getStringArray(textArrayResId)[position]
                return convertView
            } ?: run {
                val view = LayoutInflater.from(context).inflate(resource, parent, false)
                view.categoryText.text = "Select Deck Type"
                view.valueText.text = context.resources.getStringArray(textArrayResId)[position]
                return view
            }
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            convertView?.let {
                convertView.findViewById<TextView>(android.R.id.text1).text = context.resources.getStringArray(textArrayResId)[position]
                return convertView
            } ?: run {
                val view = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
                view.findViewById<TextView>(android.R.id.text1).text = context.resources.getStringArray(textArrayResId)[position]
                return view
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = OfflineSettingsFragment()
    }
}
