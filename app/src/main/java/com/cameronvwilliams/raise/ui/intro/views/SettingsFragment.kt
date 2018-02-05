package com.cameronvwilliams.raise.ui.intro.views


import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import android.content.Intent
import com.cameronvwilliams.raise.BuildConfig

class SettingsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collapsingToolbar.title = getString(R.string.settings)
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(context, android.R.color.transparent))

        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_white_24dp, null)
        toolbar.setNavigationOnClickListener {
            if (!activity.supportFragmentManager.popBackStackImmediate("intro", 0)) {
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.layoutRoot, IntroFragment.newInstance())
                    .setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_right
                    )
                    .addToBackStack("intro")
                    .commit()
            }
        }

        aboutRow.setOnClickListener {
            activity.supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left,
                    R.anim.slide_out_right,
                    R.anim.slide_in_right
                )
                .replace(R.id.layoutRoot, AboutFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }

        shareRow.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.text_share_app, BuildConfig.APPLICATION_ID))
            sendIntent.type = "text/plain"
            startActivity(Intent.createChooser(sendIntent, getString(R.string.text_choose_app)))
        }

        feedbackRow.setOnClickListener {
            activity.supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left,
                    R.anim.slide_out_right,
                    R.anim.slide_in_right
                )
                .replace(R.id.layoutRoot, FeedbackFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}
