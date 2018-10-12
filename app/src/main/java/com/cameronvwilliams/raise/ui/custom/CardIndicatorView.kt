package com.cameronvwilliams.raise.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.*
import android.widget.LinearLayout
import android.widget.ImageView
import com.cameronvwilliams.raise.R
import androidx.annotation.ColorRes
import android.view.Gravity
import android.view.LayoutInflater

class CardIndicatorView

    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {

    private var viewPager: ViewPager? = null
    private var currentIndex: Int = -1

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        val params = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        params.setMargins(8, 8, 8, 8)

        val indicator = LayoutInflater.from(context).inflate(R.layout.circle_indicator, this, false)
        indicator.setOnClickListener {
            viewPager?.setCurrentItem(0, true)
        }
        indicator.findViewById<ImageView>(R.id.indicatorImage).setImageResource(R.drawable.ic_add_white_24dp)
        indicator.layoutParams = params

        val indicator2 = LayoutInflater.from(context).inflate(R.layout.circle_indicator, this, false)
        indicator2.setOnClickListener {
            viewPager?.setCurrentItem(1, true)
        }
        indicator2.findViewById<ImageView>(R.id.indicatorImage).setImageResource(R.drawable.ic_person_add_white_24dp)
        indicator2.layoutParams = params

        val indicator3 = LayoutInflater.from(context).inflate(R.layout.circle_indicator, this, false)
        indicator3.setOnClickListener {
            viewPager?.setCurrentItem(2, true)
        }
        indicator3.findViewById<ImageView>(R.id.indicatorImage).setImageResource(R.drawable.ic_smartphone_white_24dp)
        indicator3.layoutParams = params

        addView(indicator)
        addView(indicator2)
        addView(indicator3)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        (getChildAt(0).findViewById<ImageView>(R.id.indicatorImage).background as GradientDrawable).setColor(getColor(-1))
        (getChildAt(1).findViewById<ImageView>(R.id.indicatorImage).background as GradientDrawable).setColor(getColor(-1))
        (getChildAt(2).findViewById<ImageView>(R.id.indicatorImage).background as GradientDrawable).setColor(getColor(-1))

        if (currentIndex > -1) {
            (getChildAt(currentIndex).findViewById<ImageView>(R.id.indicatorImage).background as GradientDrawable).setColor(getColor(currentIndex))
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        print("")
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        print("")
    }

    override fun onPageSelected(position: Int) {
        if (currentIndex > -1) {
            (getChildAt(currentIndex).findViewById<ImageView>(R.id.indicatorImage).background as GradientDrawable).setColor(getColor(-1))
        }

        (getChildAt(position).findViewById<ImageView>(R.id.indicatorImage).background as GradientDrawable).setColor(getColor(position))
        currentIndex = position
    }

    fun setViewPager(view: ViewPager, initialPosition: Int) {
        view.addOnPageChangeListener(this)
        viewPager = view
        setCurrentItem(initialPosition)
    }

    private fun setCurrentItem(item: Int) {
        viewPager?.let {
            currentIndex = item
            it.currentItem = item
            (getChildAt(item).findViewById<ImageView>(R.id.indicatorImage).background as GradientDrawable).setColor(getColor(item))
        } ?: throw UnsupportedOperationException("View Pager must be initialized")
    }

    private fun getColor(index: Int): Int {
        return when (index) {
            0 -> Color.parseColor("#76c23b")
            1 -> Color.parseColor("#ff9800")
            2 -> Color.parseColor("#53a1ff")
            else -> Color.parseColor("#131822")
        }
    }
}