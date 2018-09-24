package com.cameronvwilliams.raise.ui.intro.views

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.Navigator
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
        navigator.goToOfflineView(DeckType.FIBONACCI)
    }

    companion object {
        fun newInstance(): OfflineFragment {
            return OfflineFragment()
        }
    }
}