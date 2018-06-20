package com.cameronvwilliams.raise.ui.intro.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.BuildConfig

import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.intro.presenters.AboutPresenter
import com.jakewharton.rxbinding2.support.v7.widget.navigationClicks
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.intro_about_fragment.*
import javax.inject.Inject

class AboutFragment : BaseFragment() {

    @Inject
    lateinit var presenter: AboutPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_about_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated(this)

        toolbar.title = getString(R.string.text_about)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_white_24dp, null)
        versionText.text = getString(R.string.text_app_version, BuildConfig.VERSION_NAME)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDestroyed()
    }

    override fun onBackPressed(): Boolean {
        return presenter.onBackPressed()
    }

    fun toolBarNavigationClicks(): Observable<Unit> = toolbar.navigationClicks()

    fun privacyPolicyClicks(): Observable<Unit> = privacyPolicyRow.clicks()

    fun termsClicks(): Observable<Unit> = termsRow.clicks()

    companion object {
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }
}
