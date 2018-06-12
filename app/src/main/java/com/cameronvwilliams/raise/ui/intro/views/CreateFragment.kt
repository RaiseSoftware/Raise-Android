package com.cameronvwilliams.raise.ui.intro.views

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.BuildConfig
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.data.model.DeckType
import com.cameronvwilliams.raise.di.ActivityContext
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.intro.presenters.CreatePresenter
import com.cameronvwilliams.raise.util.onAdClosed
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.checkedChanges
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.intro_create_fragment.*
import javax.inject.Inject

class CreateFragment : BaseFragment() {

    @Inject
    lateinit var presenter: CreatePresenter

    @field:[Inject ActivityContext]
    lateinit var activityContext: Context

    private lateinit var intersitialAd: InterstitialAd

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        intersitialAd = InterstitialAd(activityContext)
        intersitialAd.adUnitId = BuildConfig.AD_UNIT_ID
        intersitialAd.loadAd(AdRequest.Builder().build())

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
        intersitialAd.onAdClosed {
            it.onNext(Unit)
        }
    }

    fun showLoadingView() {
        createGameForm.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoadingView() {
        createGameForm.visibility = View.VISIBLE
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

    fun showInterstitialAd() {
        intersitialAd.show()
    }

    fun shouldShowInterstitialAd(): Boolean {
        return intersitialAd.isLoaded
    }

    companion object {
        fun newInstance(): CreateFragment {
            return CreateFragment()
        }
    }
}
