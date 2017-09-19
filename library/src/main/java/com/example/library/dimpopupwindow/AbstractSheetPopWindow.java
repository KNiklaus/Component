package com.example.library.dimpopupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

/**
 * Created by Niklaus on 2017/9/19.
 */

abstract class AbstractSheetPopWindow {
    protected Context mContext;
    protected PopupWindow mPopupWindow;
    protected DimPopupHelper mDimPopupHelper;
    protected FrameLayout mFlContainer;
    protected boolean mInitedFlag;

    protected AbstractSheetPopWindow(Context context) {
        mContext = context;
        init();
    }

    /**
     * create a PopupWindow instance and set its contentView
     */
    private void init() {
        mFlContainer = new FrameLayout(mContext);
        mPopupWindow = new PopupWindow(mFlContainer, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        View view = createView();
        if (view != null) {
            mFlContainer.addView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mInitedFlag = true;
        }

        setAnimationStyle();

        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mDimPopupHelper = new DimPopupHelper(mContext, mPopupWindow);
    }

    protected ViewGroup getParent() {
        return mFlContainer;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener){
        mPopupWindow.setOnDismissListener(listener);
    }

    protected void setContentView() {
        if (!mInitedFlag) {
            final View view = createView();
            if (view != null) {
                mFlContainer.removeAllViews();
                mFlContainer.addView(view);
            }
        }
    }

    /**
     * set show() and dismiss() animation style
     */
    protected abstract void setAnimationStyle();

    /**
     * create contentView of the popupWindow
     *
     * @return
     */
    protected abstract View createView();

    public abstract void show();

    public abstract void dismiss();
}
