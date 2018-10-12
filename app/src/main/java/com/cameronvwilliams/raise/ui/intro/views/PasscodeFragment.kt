package com.cameronvwilliams.raise.ui.intro.views

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.intro.presenters.PasscodePresenter
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.intro_passcode_fragment.*
import javax.inject.Inject

class PasscodeFragment : BaseFragment() {

    @Inject
    lateinit var presenter: PasscodePresenter

    companion object BundleOptions {
        private const val EXTRA_GAME_ID = "game_id"
        private const val EXTRA_PLAYER = "player"

        fun newInstance(): PasscodeFragment {
            return PasscodeFragment()
        }

        fun Bundle.getGameName(): String {
            return getString(EXTRA_GAME_ID, "")
        }

        fun Bundle.setGameName(gameId: String) {
            putString(EXTRA_GAME_ID, gameId)
        }

        fun Bundle.getPlayer(): Player {
            return getParcelable(EXTRA_PLAYER)
        }

        fun Bundle.setPlayer(player: Player) {
            putParcelable(EXTRA_PLAYER, player)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_passcode_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated(this)

        submitButton.setOnClickListener {
            var gameName = ""
            var player: Player? = null
            with(BundleOptions) {
                gameName = arguments!!.getGameName()
                player = arguments!!.getPlayer()
            }

            presenter.onSubmitButtonClick(gameName, passcodeEditText.text.toString(), player!!.name!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDestroyed()
    }

    override fun onBackPressed(): Boolean {
        return presenter.onBackPressed()
    }

    fun backPresses(): Observable<Unit> = backButton.clicks()

    fun passocdeChanges(): Observable<CharSequence> = passcodeEditText.textChanges()
        .map { it.trim() }

    fun qrCodeScanRequests(): Observable<Unit> = barcodeText.clicks().share()

    fun submitRequests(): Observable<Unit> = submitButton.clicks()

    fun enableSubmitButton() {
        submitButton.isEnabled = true
    }

    fun disableSubmitButton() {
        submitButton.isEnabled = false
    }

    fun showLoadingView() {

    }

    fun hideLoadingView() {

    }

    fun showDefaultErrorSnackBar() {
        Snackbar.make(passcodeView, getString(R.string.error_try_again), Snackbar.LENGTH_LONG)
            .show()
    }

    fun showErrorSnackBar(message: String) {
        Snackbar.make(passcodeView, message, Snackbar.LENGTH_LONG).show()
    }
}
