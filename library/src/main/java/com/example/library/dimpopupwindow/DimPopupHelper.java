package com.example.library.dimpopupwindow;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Niklaus on 2017/9/19.
 */
class DimPopupHelper {
    private Context mContext;
    private final Dialog mDimDialog; /*dialog that can create the dim background*/
    private PopupWindow mPopup;
    private Handler mHandler;

    public DimPopupHelper(Context context) {
        this(context, null);
    }

    public DimPopupHelper(Context context, PopupWindow popup) {
        mContext = context;
        mHandler = new Handler();
        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Translucent_NoTitleBar);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        dialog.setContentView(new View(context));
        dialog.setCanceledOnTouchOutside(true);
        mDimDialog = dialog;
        if (popup != null) {
            setPopupWindow(popup);
        }
    }

    public void setPopupWindow(PopupWindow popup) {
        mPopup = popup;
        mPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //必须等popup dismiss之后才可以dismiss dialog
                //默认的animation是200ms
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDimDialog.dismiss();
                    }
                }, 200);
            }
        });
    }

    public void showPopupAtLocation(final int gravity, final int x, final int y) {
        mDimDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                mPopup.showAtLocation(mDimDialog.getWindow().getDecorView(), gravity, x, y);
            }
        });
        mDimDialog.show();
    }
}