package com.cameronvwilliams.raise.ui.intro.offline

import android.os.Bundle
import androidx.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import kotlinx.android.synthetic.main.intro_offline_card_view.*
import com.cameronvwilliams.raise.ui.Navigator
import javax.inject.Inject

class OfflineCardFragment: BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_offline_card_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val t = ChangeBounds()
        t.duration = 300L

        parentFragment?.enterTransition = t
        enterTransition = t

        parentFragment?.exitTransition = t
        exitTransition = t

        tshirtRadio.isChecked = true

        offlineCardView.setOnClickListener {
            navigator.goToOffline(offlineCardView, selectDeckText, fibonacciRadio, tshirtRadio)
        }
    }

    companion object {
        fun newInstance(): OfflineCardFragment {
            return OfflineCardFragment()
        }
    }
}