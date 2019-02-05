package com.cameronvwilliams.raise.ui.offline.view


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.ArrayRes
import androidx.annotation.LayoutRes
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.custom.RoundBottomSheetDialogFragment
import com.cameronvwilliams.raise.ui.offline.presenter.OfflineSettingsPresenter
import com.jakewharton.rxbinding2.widget.itemClicks
import com.jakewharton.rxbinding2.widget.itemSelections
import com.jakewharton.rxbinding2.widget.selection
import com.jakewharton.rxbinding2.widget.selectionEvents
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.deck_type_spinner_item.view.*
import kotlinx.android.synthetic.main.offline_settings_fragment.*
import javax.inject.Inject

class OfflineSettingsFragment : RoundBottomSheetDialogFragment() {

    @Inject
    lateinit var presenter: OfflineSettingsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.offline_settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        presenter.onViewCreated(this)

        val adapter = DeckTypeAdapter(requireContext(), R.layout.deck_type_spinner_item, R.array.text_deck_types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        deckTypeChoices.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDestroyed()
    }

    fun deckTypeSelections(): Observable<Int> = deckTypeChoices
        .itemSelections()
        .skipInitialValue()
        .distinctUntilChanged()

    fun setSelectedItem(index: Int) {
        deckTypeChoices.setSelection(index)
    }

    inner class DeckTypeAdapter(context: Context, @LayoutRes val resource: Int, @ArrayRes val textArrayResId: Int) :
        ArrayAdapter<String>(context, resource) {

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
