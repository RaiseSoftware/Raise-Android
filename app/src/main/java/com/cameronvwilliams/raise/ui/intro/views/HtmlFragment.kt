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
import kotlinx.android.synthetic.main.fragment_html.*

class HtmlFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_html, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = getString(R.string.text_about)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_white_24dp, null)
        toolbar.setNavigationOnClickListener {
            activity.supportFragmentManager.popBackStackImmediate()
        }

        var docName = ""
        with(BundleOptions) {
            docName = arguments!!.getDocName()
        }

        when(docName) {
            "privacy_policy" -> initializeText("Privacy Policy", Html.fromHtml(getString(R.string.html_privacy_policy)))
            "terms_and_conditions" -> initializeText("Terms and Conditions", Html.fromHtml(getString(R.string.html_terms_conditions)))
        }
    }

    private fun initializeText(title: String, body: Spanned) {
        toolbar.title = title
        text.text = body
        text.movementMethod = LinkMovementMethod.getInstance()
    }

    companion object BundleOptions {
        private const val EXTRA_DOC_NAME = "doc_name"

        fun newInstance(): HtmlFragment {
            return HtmlFragment()
        }

        fun Bundle.getDocName(): String {
            return getString(EXTRA_DOC_NAME)
        }

        fun Bundle.setDocName(docName: String) {
            putString(EXTRA_DOC_NAME, docName)
        }
    }
}
