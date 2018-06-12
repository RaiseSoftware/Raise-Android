package com.cameronvwilliams.raise.ui.intro.views

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.intro.presenters.JoinPresenter
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.intro_join_fragment.*
import javax.inject.Inject

class JoinFragment : BaseFragment() {

    @Inject
    lateinit var presenter: JoinPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_join_fragment, container, false)
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

    fun joinGameRequests(): Observable<Unit> = joinButton.clicks()

    fun backPresses(): Observable<Unit> = backButton.clicks()

    fun qrCodeScanRequests(): Observable<Unit> = barcodeText.clicks().share()

    fun nameChanges(): Observable<CharSequence> = userNameEditText.textChanges()
        .map { it.trim() }

    fun gameIdChanges(): Observable<CharSequence> = gameIdEditText.textChanges()
        .map { it.trim() }

    fun showLoadingView() {
        inputWrapper.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoadingView() {
        inputWrapper.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    fun showDefaultErrorSnackBar() {
        Snackbar.make(joinGameView, getString(R.string.error_network), Snackbar.LENGTH_LONG).show()
    }

    fun showErrorSnackBar(message: String) {
        Snackbar.make(joinGameView, message, Snackbar.LENGTH_LONG).show()
    }

    fun enableJoinButton() {
        joinButton.isEnabled = true
    }

    fun disableJoinButton() {
        joinButton.isEnabled = false
    }

    fun showQRCodeSuccessView() {
        qrCodeSuccessText.visibility = View.VISIBLE
        checkMark.visibility = View.VISIBLE
        scanQRCodeText.visibility = View.VISIBLE

        orDividerText.visibility = View.GONE
        fillFormText.visibility = View.GONE
        formDivider.visibility = View.GONE
        gameIdEditText.visibility = View.GONE
        barcodeText.visibility = View.GONE
    }

    companion object {
        fun newInstance(): JoinFragment {
            return JoinFragment()
        }
    }
}
