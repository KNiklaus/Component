package com.example.niklaus.dagger.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.library.R

/**
 * Created by Niklaus on 2017/9/1.
 */
class BottomBar @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyle: Int = 0)
    : LinearLayout(context, attr, defStyle) {

    private var mTabLayout: LinearLayout
    private var mTabParams: LinearLayout.LayoutParams
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var mListener: OnTabSelectListener
    private var mCurrentPosition: Int = 0
    private var mTabs: MutableList<BottomBarTab> = mutableListOf()
    private var mShadowBitmap: Bitmap

    init {
        clipChildren = false
        setWillNotDraw(false)

        orientation = VERTICAL
        mTabLayout = LinearLayout(context)
        mTabLayout.orientation = HORIZONTAL
        addView(mTabLayout, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        mTabParams = LayoutParams(0, LayoutParams.MATCH_PARENT)
        mTabParams.weight = 1f

        mShadowBitmap = BitmapFactory.decodeResource(resources, R.drawable.segment_bottom_shadow)
        mShadowBitmap = resizeBitmap(mShadowBitmap, resources.displayMetrics.widthPixels, mShadowBitmap.height / 2)
    }

    private fun resizeBitmap(bitmap: Bitmap, w: Int, h: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val scaleWight = w.toFloat() / width
        val scaleHeight = h.toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWight, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(mShadowBitmap, 0f, 0f, mPaint)
        super.onDraw(canvas)
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
        tab.layoutParams = mTabParams
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