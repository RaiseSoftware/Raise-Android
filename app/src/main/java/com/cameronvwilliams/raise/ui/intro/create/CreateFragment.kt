package com.cameronvwilliams.raise.ui.intro.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.intro.presenters.CreatePresenter
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.checkedChanges
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.intro_create_fragment.*
import javax.inject.Inject


class CreateFragment : BaseFragment() {

    @Inject
    lateinit var presenter: CreatePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.strong_pistachio)
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

    fun createGameRequests() = createButton.clicks()

    fun backPresses() = backButton.clicks()

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

    fun enableCreateButton() {
        createButton.isEnabled = true
    }

    fun disableCreateButton() {
        createButton.isEnabled = false
    }

    companion object {
        fun newInstance(): CreateFragment {
            return CreateFragment()
        }
    }
}
