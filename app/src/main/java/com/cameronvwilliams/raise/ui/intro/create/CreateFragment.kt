package com.cameronvwilliams.raise.ui.intro.create

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.intro.presenters.CreatePresenter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.intro_create_fragment.*
import javax.inject.Inject
import androidx.transition.Transition
import com.cameronvwilliams.raise.ui.Navigator
import android.animation.ObjectAnimator
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.checkedChanges
import com.jakewharton.rxbinding2.widget.textChanges


class CreateFragment : BaseFragment() {

    @Inject
    lateinit var presenter: CreatePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.strong_pistachio)

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
                createCardView.radius = 36F
                val animator = ObjectAnimator
                    .ofFloat(createCardView, "radius", 0F)
                animator.duration = 300
                animator.start()
            }
        })

        return inflater.inflate(R.layout.intro_create_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDestroyed()
    }

    override fun onBackPressed(): Boolean {
        return presenter.onBackPressed()
    }

    fun createGameRequests(): Observable<Unit> = createButton.clicks()

    fun backPresses(): Observable<Unit> = backButton.clicks()

    fun deckTypeChanges(): Observable<DeckType> = deckTypeRadioGroup.checkedChanges()
        .map { index: Int ->
            val selectedRadioButtonIndex = deckTypeRadioGroup.indexOfChild(
                deckTypeRadioGroup.findViewById(index)
            )
            return@map when (selectedRadioButtonIndex) {
                0 -> DeckType.FIBONACCI
                1 -> DeckType.T_SHIRT
                else -> DeckType.NONE
            }
        }

    fun gameNameChanges(): Observable<CharSequence> = gameNameText.textChanges()
        .map { it.trim() }

    fun nameChanges(): Observable<CharSequence> = userNameEditText.textChanges()
        .map { it.trim() }

    fun passcodeChanges(): Observable<Boolean> = requirePasscodeCheckbox.checkedChanges()

    fun adClosed(): Observable<Unit> = Observable.create {
    }

    fun showLoadingView() {
        group.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoadingView() {
        joinForm.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    fun enableCreateButton() {
        createButton.isEnabled = true
    }

    fun disableCreateButton() {
        createButton.isEnabled = false
    }

    fun showDefaultErrorSnackBar() {
        Snackbar.make(createGameView, getString(R.string.error_network), Snackbar.LENGTH_LONG)
            .show()
    }

    companion object {
        fun newInstance(): CreateFragment {
            return CreateFragment()
        }
    }
}
