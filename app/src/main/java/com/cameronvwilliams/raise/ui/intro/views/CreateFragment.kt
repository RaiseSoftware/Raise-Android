package com.cameronvwilliams.raise.ui.intro.views

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.BuildConfig
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.di.ActivityContext
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.intro.IntroContract
import com.cameronvwilliams.raise.util.onAdClosed
import com.cameronvwilliams.raise.util.onChange
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.intro_create_fragment.*
import javax.inject.Inject

class CreateFragment : BaseFragment(), IntroContract.CreateViewActions {

    @Inject
    lateinit var actions: IntroContract.CreateUserActions
    @field:[Inject ActivityContext]
    lateinit var activityContext: Context

    private lateinit var intersitialAd: InterstitialAd

    override val RADIO_FIBONACCI: Int
        get() = 0
    override val RADIO_T_SHIRT: Int
        get() = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        intersitialAd = InterstitialAd(activityContext)
        intersitialAd.adUnitId = BuildConfig.AD_UNIT_ID
        intersitialAd.loadAd(AdRequest.Builder().build())

        intersitialAd.onAdClosed {
            actions.onAdClosed()
        }

        return inflater.inflate(R.layout.intro_create_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actions.onViewCreated(this)

        backButton.setOnClickListener {
            actions.onBackPressed()
        }

        deckTypeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            actions.onDeckTypeRadioButtonChecked(gameNameText.text.toString(), checkedId != -1)
        }

        gameNameText.onChange { s ->
            actions.onGameNameTextChanged(s, deckTypeRadioGroup.checkedRadioButtonId != -1)
        }

        createButton.setOnClickListener {
            val selectedRadioButtonIndex = deckTypeRadioGroup.indexOfChild(
                deckTypeRadioGroup.findViewById(deckTypeRadioGroup.checkedRadioButtonId)
            )

            actions.onCreateButtonClicked(
                gameNameText.text.toString().trim(),
                userNameEditText.text.toString().trim(),
                selectedRadioButtonIndex,
                requirePasscodeCheckbox.isChecked
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        actions.onViewDestroyed()
    }

    override fun onBackPressed(): Boolean {
        return actions.onBackPressed()
    }

    override fun showLoadingView() {
        createGameForm.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoadingView() {
        createGameForm.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun showErrorMessage() {
        Snackbar.make(createGameView, getString(R.string.error_try_again), Snackbar.LENGTH_LONG)
            .show()
    }

    override fun enableCreateButton() {
        createButton.isEnabled = true
    }

    override fun disableCreateButton() {
        createButton.isEnabled = false
    }

    override fun showErrorSnackBar(message: String) {
        Snackbar.make(createGameView, message, Snackbar.LENGTH_LONG).show()
    }

    override fun showDefaultErrorSnackBar() {
        Snackbar.make(createGameView, getString(R.string.error_network), Snackbar.LENGTH_LONG)
            .show()
    }

    override fun showInterstitialAd() {
        intersitialAd.show()
    }

    override fun shouldShowInterstitialAd(): Boolean {
        return intersitialAd.isLoaded
    }

    companion object {
        fun newInstance(): CreateFragment {
            return CreateFragment()
        }
    }
}
