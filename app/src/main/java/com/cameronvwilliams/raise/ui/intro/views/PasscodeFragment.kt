package com.cameronvwilliams.raise.ui.intro.views

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.Player
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.intro.IntroContract
import com.cameronvwilliams.raise.util.onChange
import kotlinx.android.synthetic.main.fragment_passcode.*
import javax.inject.Inject

class PasscodeFragment : BaseFragment(), IntroContract.PasscodeViewActions {

    @Inject
    lateinit var actions: IntroContract.PasscodeUserActions

    companion object BundleOptions {
        private const val EXTRA_GAME_ID = "game_id"
        private const val EXTRA_PLAYER = "player"

        fun newInstance(): PasscodeFragment {
            return PasscodeFragment()
        }

        fun Bundle.getGameId(): String {
            return getString(EXTRA_GAME_ID)
        }

        fun Bundle.setGameId(gameId: String) {
            putString(EXTRA_GAME_ID, gameId)
        }

        fun Bundle.getPlayer(): Player {
            return getParcelable(EXTRA_PLAYER)
        }

        fun Bundle.setPlayer(player: Player) {
            putParcelable(EXTRA_PLAYER, player)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_passcode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actions.onViewCreated(this)

        backButton.setOnClickListener {
            activity!!.onBackPressed()
        }

        passcodeEditText.onChange { s -> actions.onPasscodeTextChanged(s) }

        submitButton.setOnClickListener {
            var gameId = ""
            var player: Player? = null
            with(BundleOptions) {
                gameId = arguments!!.getGameId()
                player = arguments!!.getPlayer()
            }

            actions.onSubmitButtonClick(gameId, passcodeEditText.text.toString(), player!!.userName)
        }
    }

    override fun enableSubmitButton() {
        submitButton.isEnabled = true
    }

    override fun disableSubmitButton() {
        submitButton.isEnabled = false
    }

    override fun showLoadingView() {

    }

    override fun hideLoadingView() {

    }

    override fun showDefaultErrorSnackBar() {
        Snackbar.make(passcodeView, getString(R.string.error_try_again), Snackbar.LENGTH_LONG)
            .show()
    }

    override fun showErrorSnackBar(message: String) {
        Snackbar.make(passcodeView, message, Snackbar.LENGTH_LONG).show()
    }
}
