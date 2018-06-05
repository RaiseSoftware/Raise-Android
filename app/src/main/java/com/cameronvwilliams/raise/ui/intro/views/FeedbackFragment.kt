package com.cameronvwilliams.raise.ui.intro.views


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.BuildConfig

import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import kotlinx.android.synthetic.main.intro_feedback_fragment.*
import timber.log.Timber

class FeedbackFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_feedback_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = getString(R.string.text_feedback)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_white_24dp, null)
        toolbar.setNavigationOnClickListener {
            activity!!.supportFragmentManager.popBackStackImmediate()
        }

        rateUsRow.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)))
            } catch (e: android.content.ActivityNotFoundException) {
                Timber.e(e)
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)
                    )
                )
            }
        }

        issueRow.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/RaiseSoftware/Raise-Android/issues")))
        }
    }

    companion object {
        fun newInstance(): FeedbackFragment {
            return FeedbackFragment()
        }
    }
}