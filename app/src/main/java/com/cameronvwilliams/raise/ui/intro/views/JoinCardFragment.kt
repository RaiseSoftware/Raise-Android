package com.cameronvwilliams.raise.ui.intro.views

import android.os.Bundle
import androidx.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.Navigator
import kotlinx.android.synthetic.main.intro_join_card_view.*
import javax.inject.Inject

class JoinCardFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_join_card_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val t = ChangeBounds()
        t.duration = 300L

        parentFragment?.enterTransition = t
        enterTransition = t

        parentFragment?.exitTransition = t
        exitTransition = t

        joinCardView.setOnClickListener {
            navigator.goToJoinGame(
                joinCardView,
                fillFormText,
                joinForm,
                userNameEditText,
                formDivider,
                gameIdEditText,
                orDividerText,
                barcodeText
            )
        }
    }

    companion object {
        fun newInstance(): JoinCardFragment {
            return JoinCardFragment()
        }
    }
}