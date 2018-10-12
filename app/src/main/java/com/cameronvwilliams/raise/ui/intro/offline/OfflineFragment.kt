package com.cameronvwilliams.raise.ui.intro.offline

import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.Navigator
import kotlinx.android.synthetic.main.intro_offline_fragment.*
import javax.inject.Inject

class OfflineFragment: BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.tall_blue)
        return inflater.inflate(R.layout.intro_offline_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener {
            navigator.goBack()
        }

        startButton.setOnClickListener {
            navigator.goToOfflineView(DeckType.FIBONACCI)
        }

        deckTypeRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            if (radioGroup.checkedRadioButtonId != -1) {
                startButton.isEnabled = true
            }
        }
    }

    companion object {
        fun newInstance(): OfflineFragment {
            return OfflineFragment()
        }
    }
}