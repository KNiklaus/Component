package com.example.library.dimpopupwindow;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.library.R;


/**
 * Created by Niklaus on 2017/9/19.
 */

public final class BottomLeftRightSheetPopWindow extends AbstractBottomSheetPopWindow {
    private FrameLayout mFlBtnLeft, mFlBtnRight;
    private TextView mTvBtnCancel;
    private TextView mTvLeft, mTvRight;


    private BottomLeftRightSheetPopWindow(Context context) {
        super(context);
    }

    /**
     * 此方法由父类调用,返回一个View的实例作为PopupWindow的contentView
     *
     * @return
     */
    @Override
    protected View createView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dimpopupwindow_bottom, null);
        mFlBtnLeft = view.findViewById(R.id.fl_btn_left);
        mFlBtnRight = view.findViewById(R.id.fl_btn_right);
        mTvBtnCancel = view.findViewById(R.id.tv_btn_cancel);
        mTvLeft = view.findViewById(R.id.tv_left);
        mTvRight = view.findViewById(R.id.tv_right);

        mTvBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }

    /**
     * 设置左布局的icon和text(private权限保证只能通过builder构造状态一致的BottomSheetPopWindow)
     *
     * @param leftIcon
     * @param leftText
     * @param leftClickListener
     */
    private void setLeft(@DrawableRes int leftIcon, CharSequence leftText, View.OnClickListener leftClickListener) {
        mTvLeft.setText(leftText);
        mTvLeft.setCompoundDrawablesWithIntrinsicBounds(0, leftIcon, 0, 0);
        mFlBtnLeft.setOnClickListener(leftClickListener);
    }

    /**
     * 设置右布局的icon和text(private权限保证只能通过builder构造状态一致的BottomSheetPopWindow)
     *
     * @param rightIcon
     * @param rightText
     * @param rightClickListener
     */
    private void setRight(@DrawableRes int rightIcon, CharSequence rightText, View.OnClickListener rightClickListener) {
        mTvRight.setText(rightText);
        mTvRight.setCompoundDrawablesWithIntrinsicBounds(0, rightIcon, 0, 0);
        mFlBtnRight.setOnClickListener(rightClickListener);
    }

    public static class Builder {
        private Context context;
        private int leftIcon;
        private CharSequence leftText;
        private int rightIcon;
        private CharSequence rightText;
        private View.OnClickListener leftClickListener, rightClickListener;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        /**
         * 设置左布局的icon和text
         *
         * @param leftIcon
         * @param leftText
         * @param leftClickListener
         */
        public Builder setLeft(@DrawableRes int leftIcon, CharSequence leftText, View.OnClickListener leftClickListener) {
            this.leftIcon = leftIcon;
            this.leftText = leftText;
            this.leftClickListener = leftClickListener;
            return this;
        }

        /**
         * 设置右布局的icon和text
         *
         * @param rightIcon
         * @param rightText
         * @param rightClickListener
         */
        public Builder setRight(@DrawableRes int rightIcon, CharSequence rightText, View.OnClickListener rightClickListener) {
            this.rightIcon = rightIcon;
            this.rightText = rightText;
            this.rightClickListener = rightClickListener;
            return this;
        }

        /**
         * create BottomLeftRightSheetPopWindow instance
         *
         * @return
         */
        public BottomLeftRightSheetPopWindow create() {
            BottomLeftRightSheetPopWindow popup = new BottomLeftRightSheetPopWindow(context);
            popup.setLeft(leftIcon, leftText, leftClickListener);
            popup.setRight(rightIcon, rightText, rightClickListener);
            return popup;
        }
    }
}
