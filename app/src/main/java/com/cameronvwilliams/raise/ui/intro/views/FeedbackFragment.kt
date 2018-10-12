package com.cameronvwilliams.raise.ui.intro.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.intro.presenters.FeedbackPresenter
import com.jakewharton.rxbinding2.support.v7.widget.navigationClicks
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.intro_feedback_fragment.*
import javax.inject.Inject

class FeedbackFragment : BaseFragment() {

    @Inject
    lateinit var presenter: FeedbackPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_feedback_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated(this)

        toolbar.title = getString(R.string.text_feedback)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_white_24dp, null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDestroyed()
    }

    fun toolBarNavigationClicks(): Observable<Unit> = toolbar.navigationClicks()

    fun rateUsClicks(): Observable<Unit> = rateUsRow.clicks()

    fun issueClicks(): Observable<Unit> = issueRow.clicks()

    companion object {
        fun newInstance(): FeedbackFragment {
            return FeedbackFragment()
        }
    }
}