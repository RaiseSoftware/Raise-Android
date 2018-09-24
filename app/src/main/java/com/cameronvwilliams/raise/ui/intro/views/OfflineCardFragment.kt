package com.cameronvwilliams.raise.ui.intro.views

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.ui.BaseFragment
import kotlinx.android.synthetic.main.intro_offline_card_view.*
import javax.inject.Inject

class OfflineCardFragment: BaseFragment() {

    @Inject
    lateinit var prefs: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!prefs.contains("offline_deck_type")) {
            prefs.edit().putString("offline_deck_type", DeckType.FIBONACCI.toString()).apply()
        }
        return inflater.inflate(R.layout.intro_offline_card_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val d: DeckType = DeckType.valueOf(prefs.getString("offline_deck_type", DeckType.T_SHIRT.toString()))

        tshirtRadio.isChecked = true

        offlineCardView.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.layoutRoot, OfflineFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        fun newInstance(): OfflineCardFragment {
            return OfflineCardFragment()
        }
    }
}