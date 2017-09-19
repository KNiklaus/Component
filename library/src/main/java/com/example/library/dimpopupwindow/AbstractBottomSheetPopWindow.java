package com.example.library.dimpopupwindow;

import android.content.Context;
import android.view.Gravity;

import com.example.library.R;


/**
 * Created by Niklaus on 2017/9/19.
 */
abstract class AbstractBottomSheetPopWindow extends AbstractSheetPopWindow {

    protected AbstractBottomSheetPopWindow(Context context) {
        super(context);
    }

    @Override
    protected void setAnimationStyle() {
        mPopupWindow.setAnimationStyle(R.style.dimpopwindow_anim_bottom);
    }

    @Override
    public void show() {
        setContentView();
        mDimPopupHelper.showPopupAtLocation(Gravity.BOTTOM, 0, 0);
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}
