package com.cameronvwilliams.raise.ui.intro.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.intro.presenters.SettingsPresenter
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.intro_settings_fragment.*
import javax.inject.Inject

class SettingsFragment : BaseFragment() {

    @Inject
    lateinit var presenter: SettingsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_settings_fragment, container, false)
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

    fun aboutClicks(): Observable<Unit> = aboutRow.clicks()

    fun shareClicks(): Observable<Unit> = shareRow.clicks()

    fun feedbackClicks(): Observable<Unit> = feedbackRow.clicks()

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}
