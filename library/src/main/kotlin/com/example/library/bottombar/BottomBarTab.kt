package com.example.niklaus.dagger.widget

import android.content.Context
import android.os.Build
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.library.util.DensityUtil

/**
 * Created by Niklaus on 2017/9/1.
 */
class BottomBarTab : FrameLayout {

    private lateinit var mImageView: ImageView
    private lateinit var mTextView: TextView
    private var mTextSelectColor: Int = 0
    private var mTextUnSelectColor: Int = 0
    private var mIconSelect: Int = 0
    private var mIconUnSelect: Int = 0
    private var mOnSelectOnListener: OnSelectListener? = null
    private var mPosition = -1
    private var mText: CharSequence = ""

    constructor(context: Context,
                text: CharSequence,
                textSelectColor: Int,
                textUnSelectColor: Int,
                @DrawableRes iconSelect: Int,
                @DrawableRes iconUnSelect: Int,
                onSelectListener: OnSelectListener) : this(context, null) {

        init(text,textSelectColor,textUnSelectColor,iconSelect,iconUnSelect,onSelectListener)
    }

    constructor(context: Context,
                attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context, attr: AttributeSet?, defStyle: Int) : super(context, attr, defStyle)

    companion object {

        @JvmStatic
        fun newTab(
                context: Context,
                text: CharSequence,
                textSelectColor: Int,
                textUnSelectColor: Int,
                iconSelect: Int,
                iconUnSelect: Int,
                onSelectListener: OnSelectListener): BottomBarTab = BottomBarTab(context, text, textSelectColor, textUnSelectColor, iconSelect, iconUnSelect, onSelectListener)
    }

    private fun init(text: CharSequence, textSelectColor: Int, textUnSelectColor: Int, iconSelect: Int, iconUnSelect: Int, onSelectListener: OnSelectListener) {
        mTextSelectColor = textSelectColor
        mTextUnSelectColor = textUnSelectColor
        mIconSelect = iconSelect
        mIconUnSelect = iconUnSelect
        mOnSelectOnListener = onSelectListener
        mText = text
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            var typedOut = TypedValue()
            context.theme.resolveAttribute(android.R.attr.selectableItemBackground, typedOut, true)
            setBackgroundResource(typedOut.resourceId)
        }

        var linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.gravity = Gravity.CENTER

        var paramas = LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        paramas.gravity = Gravity.CENTER_HORIZONTAL
        linearLayout.layoutParams = paramas

        val dp32 = DensityUtil.dip2px(context,32f)
        mImageView = ImageView(context)
        mImageView.layoutParams = LayoutParams(dp32, dp32)
        linearLayout.addView(mImageView)

        mTextView = TextView(context)
        mTextView.text = mText
        var tvParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,10f)
        mTextView.layoutParams = tvParams
        linearLayout.addView(mTextView)

        addView(linearLayout)

        isSelected = false
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)

        if (mOnSelectOnListener != null && mIconSelect == 0 && mIconUnSelect == 0) {
            mOnSelectOnListener?.onSelected(mImageView, mTextView, false)
        } else {
            mImageView.setImageResource(if (selected) mIconSelect else mIconUnSelect)
            mTextView.setTextColor(if (selected) mTextSelectColor else mTextUnSelectColor)
        }
    }

    fun setTabPosition(position: Int) {
        mPosition = position
        if (position == 0) {
            isSelected = true
        }
    }

    fun getPosition(): Int = mPosition

    interface OnSelectListener {
        fun onSelected(imageView: ImageView, textView: TextView, isSelect: Boolean)
    }
}