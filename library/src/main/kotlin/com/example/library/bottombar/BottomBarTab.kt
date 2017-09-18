package com.example.niklaus.dagger.widget

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.niklaus.dagger.R

/**
 * Created by Niklaus on 2017/9/1.
 */
class BottomBarTab : FrameLayout/*constructor(
        context: Context,
        text: CharSequence,
        textSelectColor: Int,
        textUnSelectColor: Int,
        @DrawableRes iconSelect: Int,
        @DrawableRes iconUnSelect: Int,
        onSelectListener: OnSelectListener) : FrameLayout(context,attr,defStyle)*/ {

    private lateinit var mImageView: ImageView
    private lateinit var mTextView: TextView
    private lateinit var mTvMsg: TextView
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
//            var typedArray = context.obtainStyledAttributes(IntArray(android.R.attr.selectableItemBackgroundBorderless)) as TypedArray
//            var drawable = typedArray.getDrawable(0)
//            background = drawable
//            typedArray.recycle()
            var typedOut : TypedValue = TypedValue()
            getContext().theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, typedOut, true)
            setBackgroundResource(typedOut.resourceId)
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            var typedArray = context.obtainStyledAttributes(kotlin.IntArray(android.R.attr.selectableItemBackgroundBorderless))
//            var drawable = typedArray.getDrawable(0)
//            background = drawable
//            typedArray.recycle()
//        }


        var linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.gravity = Gravity.CENTER

        var paramas = LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        paramas.gravity = Gravity.CENTER_HORIZONTAL
        linearLayout.layoutParams = paramas

        val dp32 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32f, resources.displayMetrics)
        mImageView = ImageView(context)
        mImageView.layoutParams = LayoutParams(dp32.toInt(), dp32.toInt())
        linearLayout.addView(mImageView)

        mTextView = TextView(context)
        mTextView.text = mText
        var tvParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mTextView.textSize = 10f
        mTextView.layoutParams = tvParams
        linearLayout.addView(mTextView)

        addView(linearLayout)

        val min = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f, resources.displayMetrics)
        val dp17 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 17f, resources.displayMetrics)
        val dp14 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14f, resources.displayMetrics)
        mTvMsg = TextView(context)
        mTvMsg.setBackgroundResource(R.drawable.segment_bg_msg_bubble)
        mTvMsg.setTextColor(Color.WHITE)
        mTvMsg.minHeight = min.toInt()
        mTvMsg.minWidth = min.toInt()
        mTvMsg.gravity = Gravity.CENTER
        var msgParams = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,min.toInt())
//        msgParams.leftMargin = dp17.toInt()
//        msgParams.bottomMargin = dp14.toInt()
        msgParams.gravity = Gravity.CENTER
        mTvMsg.layoutParams = msgParams
        mTvMsg.visibility = View.GONE
        addView(mTvMsg)

        isSelected = false
    }

    fun setMsgCount(count: Int) : BottomBarTab{
        if (count == 0) {
            mTvMsg.visibility = View.GONE
        } else {
            mTvMsg.text = count.toString()
            mTvMsg.visibility = View.VISIBLE
        }
        return this
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