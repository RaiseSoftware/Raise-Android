package com.cameronvwilliams.raise.ui.intro.views


import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.intro.presenters.HtmlPresenter
import com.jakewharton.rxbinding2.support.v7.widget.navigationClicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.intro_html_fragment.*
import javax.inject.Inject

class HtmlFragment : BaseFragment() {

    @Inject
    lateinit var presenter: HtmlPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_html_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated(this, arguments?.getDocName())

        toolbar.title = getString(R.string.text_about)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_white_24dp, null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDestroyed()
    }

    fun toolBarNavigationClicks(): Observable<Unit> = toolbar.navigationClicks()

    fun setPrivacyPolicy() = initializeText("Privacy Policy", Html.fromHtml(getString(R.string.html_privacy_policy)))

    fun setTerms() = initializeText("Terms and Conditions", Html.fromHtml(getString(R.string.html_terms_conditions)))

    private fun initializeText(title: String, body: Spanned) {
        toolbar.title = title
        text.text = body
        text.movementMethod = LinkMovementMethod.getInstance()
    }

    companion object {
        private const val EXTRA_DOC_NAME = "doc_name"
        const val EXTRA_PRIVACY_POLICY = "privacy_policy"
        const val EXTRA_TERMS = "terms_and_conditions"

        fun newInstance(docName: String): HtmlFragment {
            val b = Bundle()
            b.setDocName(docName)

            val fragment = HtmlFragment()
            fragment.arguments = b

            return fragment
        }

        fun Bundle.getDocName(): String {
            return getString(EXTRA_DOC_NAME)
        }

        private fun Bundle.setDocName(docName: String) {
            putString(EXTRA_DOC_NAME, docName)
        }
    }
}
