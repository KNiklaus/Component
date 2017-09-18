package com.example.niklaus.dagger.widget

import android.content.Context
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import com.example.niklaus.dagger.R

/**
 * Created by Niklaus on 2017/9/1.
 */
class BottomBar @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyle: Int = 0)
    : LinearLayout(context, attr, defStyle){

    private var mTabLayout: LinearLayout
    private var mTabPamams: LinearLayout.LayoutParams
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var mListener: OnTabSelectListener
    private var mCurrentPosition: Int = 0
    private var mTabs: MutableList<BottomBarTab> = mutableListOf()
//    private var mShadowBitmap: Bitmap

    init {
        clipChildren = false
        setWillNotDraw(false)

        mPaint.color = ContextCompat.getColor(context, R.color.color_white_d9)
        mPaint.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,0.7f,resources.displayMetrics)

//        mShadowBitmap = BitmapFactory.decodeResource(resources, R.drawable.segment_bottom_shadow)
//        mShadowBitmap

        orientation = VERTICAL
        mTabLayout = LinearLayout(context)
        mTabLayout.orientation = HORIZONTAL
        addView(mTabLayout, LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT))
        mTabPamams = LayoutParams(0,LayoutParams.MATCH_PARENT)
        mTabPamams.weight = 1f

    }

    fun addTab(tab: BottomBarTab): BottomBar {
        tab.setOnClickListener {
            mListener?.let {
                val pos = tab.getPosition()
                if (mCurrentPosition == pos) {
                    mListener.onTabReSelected(pos)
                } else {
                    mListener.onTabSelected(pos, mCurrentPosition)
                    tab.isSelected = true
                    mListener.onTabUnSelected(mCurrentPosition)
                    mTabs[mCurrentPosition].isSelected = false
                    mCurrentPosition = pos
                }
            }
        }
        tab.setTabPosition(mTabLayout.childCount)
        tab.layoutParams = mTabPamams
        mTabLayout.addView(tab)
        mTabs.add(tab)
        return this
    }

    fun setCurrentTab(position: Int) {
        mTabLayout.post { mTabLayout.getChildAt(position).performClick() }
    }

    fun getCurrentTab() = mCurrentPosition

    fun getTab(index: Int): BottomBarTab? = if (mTabs.size < index) null else mTabs[index]

    interface OnTabSelectListener {
        fun onTabSelected(position: Int, prePosition: Int)

        fun onTabUnSelected(position: Int)

        fun onTabReSelected(position: Int)
    }

    fun setOnTabSelectListener(listener: OnTabSelectListener) {
        mListener = listener
    }
}