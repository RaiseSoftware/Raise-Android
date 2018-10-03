package com.cameronvwilliams.raise.ui.intro.create


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.data.model.PokerGame
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.intro.create.presenter.CreatePasscodePresenter
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.intro_create_passcode_fragment.*
import javax.inject.Inject

class CreatePasscodeFragment : BaseFragment() {

    @Inject
    lateinit var presenter: CreatePasscodePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_create_passcode_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.onViewCreated(this, arguments?.get("game") as PokerGame, Player(""))
    }

    fun passcodeChanges(): Observable<CharSequence> = passcodeEditText.textChanges()
        .map { it.trim() }

    fun createRequests(): Observable<Unit> = submitButton.clicks()

    fun enableSubmitButton() {
        submitButton.isEnabled = true
    }

    fun disableSubmitButton() {
        submitButton.isEnabled = false
    }

    fun backPresses() = backButton.clicks()

    fun showLoadingView() {
        group.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoadingView() {
        group.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    companion object {
        fun newInstance(): CreatePasscodeFragment =
            CreatePasscodeFragment()
    }
}
