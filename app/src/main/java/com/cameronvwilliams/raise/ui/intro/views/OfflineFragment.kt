package com.cameronvwilliams.raise.ui.intro.views

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.transition.Transition
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.Navigator
import kotlinx.android.synthetic.main.intro_offline_fragment.*
import javax.inject.Inject

class OfflineFragment: BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.tall_blue)

        (sharedElementEnterTransition as Navigator.Transition).addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(p0: Transition) {

            }

            override fun onTransitionResume(p0: Transition) {

            }

            override fun onTransitionPause(p0: Transition) {

            }

            override fun onTransitionCancel(p0: Transition) {

            }

            override fun onTransitionStart(p0: Transition) {
                val animator = ObjectAnimator
                    .ofFloat(offlineCardView, "radius", 0F)
                animator.duration = 300
                animator.start()
            }
        })

        return inflater.inflate(R.layout.intro_offline_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener {
            navigator.goBack()
        }
    }

    companion object {
        fun newInstance(): OfflineFragment {
            return OfflineFragment()
        }
    }
}