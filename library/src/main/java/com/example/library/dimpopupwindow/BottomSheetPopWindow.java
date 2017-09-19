package com.example.library.dimpopupwindow;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Niklaus on 2017/9/19.
 */
public class BottomSheetPopWindow extends AbstractBottomSheetPopWindow {
    private View mView;

    private BottomSheetPopWindow(Context context) {
        super(context);
    }

    @Override
    protected View createView() {
        return mView;
    }

    private void setView(View view) {
        this.mView = view;
    }

    public static class Builder {
        private Context context;
        private View view;
        private int res;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        /**
         * setContentView
         */
        public Builder setContentView(View view) {
            this.view = view;
            return this;
        }

        /**
         * setContentView
         */
        public Builder setContentView(@LayoutRes int res) {
            this.res = res;
            return this;
        }

        /**
         * create BottomSheetPopWindow instance
         *
         * @return
         */
        public BottomSheetPopWindow create() {
            BottomSheetPopWindow popup = new BottomSheetPopWindow(context);
            if (res != 0) {
                view = LayoutInflater.from(context).inflate(res, popup.getParent(), false);
            }
            popup.setView(view);
            return popup;
        }

        /**
         * show BottomSheetPopWindow
         */
        public BottomSheetPopWindow show() {
            BottomSheetPopWindow popup = create();
            popup.show();
            return popup;
        }
    }
}
