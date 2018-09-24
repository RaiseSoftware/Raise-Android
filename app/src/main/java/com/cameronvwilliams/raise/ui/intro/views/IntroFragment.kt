package com.cameronvwilliams.raise.ui.intro.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cameronvwilliams.raise.R
import com.cameronvwilliams.raise.ui.BaseFragment
import com.cameronvwilliams.raise.ui.intro.presenters.IntroPresenter
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.intro_fragment.*
import javax.inject.Inject
import android.graphics.Point
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager

class IntroFragment : BaseFragment() {

    @Inject
    lateinit var presenter: IntroPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.blacker_russian)
        return inflater.inflate(R.layout.intro_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated(this)

        val density = resources.displayMetrics.density
        val partialWidth = (16 * density).toInt()
        val pageMargin = (8 * density).toInt()
        val viewPagerPadding = partialWidth + pageMargin

        val screen = Point()
        requireActivity().windowManager.defaultDisplay.getSize(screen)
        val startOffset = viewPagerPadding.toFloat() / (screen.x - 2 * viewPagerPadding).toFloat()

        gameCards.setPageTransformer(false, CardsPagerTransformerShift(1, 4, .95f, startOffset))

        gameCards.adapter = GameCardsPagerAdapter(childFragmentManager)
        gameCards.pageMargin = pageMargin
        gameCards.setPadding(viewPagerPadding, 0, viewPagerPadding, 0)
        indicator.setViewPager(gameCards, 0)
        gameCards.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> currentCard.text = "Create Game"
                    1 -> currentCard.text = "Join Game"
                    2 -> currentCard.text = "Offline Game"
                }
            }
        })
        currentCard.text = "Create Game"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDestroyed()
    }

    override fun onBackPressed(): Boolean {
        return presenter.onBackPressed()
    }

    fun settingsButtonClicks(): Observable<Unit> = settingsButton.clicks()

    private class GameCardsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val fragments: Array<BaseFragment> = arrayOf(
            CreateCardFragment.newInstance(),
            JoinCardFragment.newInstance(),
            OfflineCardFragment.newInstance()
        )

        override fun getItem(position: Int) = fragments[position]

        override fun getCount(): Int = fragments.size
    }

    companion object {
        fun newInstance(): IntroFragment {
            return IntroFragment()
        }
    }

    inner class CardsPagerTransformerShift (
        private val baseElevation: Int,
        private val raisingElevation: Int,
        private val smallerScale: Float,
        private val startOffset: Float
    ): ViewPager.PageTransformer {

        override fun transformPage(page: View, position: Float) {
            val absPosition = Math.abs(position - startOffset)

            if (absPosition >= 1) {
                page.elevation = baseElevation.toFloat()
                page.scaleY = smallerScale
            } else {
                // This will be during transformation
                page.elevation = (1 - absPosition) * raisingElevation + baseElevation
                page.scaleY = (smallerScale - 1) * absPosition + 1
            }
        }

    }
}
