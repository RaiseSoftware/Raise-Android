package com.cameronvwilliams.raise.ui.intro.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.BuildConfig

import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = getString(R.string.text_about)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_white_24dp, null)
        toolbar.setNavigationOnClickListener {

        }
        versionText.text = "Version ${BuildConfig.VERSION_NAME}"
        privacyPolicyRow.setOnClickListener {
            val fragment = HtmlFragment.newInstance()
            val bundle = Bundle()

            with(HtmlFragment.BundleOptions) {
                bundle.setDocName("privacy_policy")
            }
            fragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left,
                    R.anim.slide_out_right,
                    R.anim.slide_in_right
                )
                .replace(R.id.layoutRoot, fragment)
                .addToBackStack(null)
                .commit()
        }

        termsRow.setOnClickListener {
            val fragment = HtmlFragment.newInstance()
            val bundle = Bundle()

            with(HtmlFragment.BundleOptions) {
                bundle.setDocName("terms_and_conditions")
            }
            fragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left,
                    R.anim.slide_out_right,
                    R.anim.slide_in_right
                )
                .replace(R.id.layoutRoot, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }
}
