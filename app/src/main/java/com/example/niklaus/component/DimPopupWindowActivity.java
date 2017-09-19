package com.example.niklaus.component;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.library.dimpopupwindow.BottomLeftRightSheetPopWindow;

/**
 * Created by Niklaus on 2017/9/19.
 */

public class DimPopupWindowActivity extends AppCompatActivity {
    private Button mButton;
    private BottomLeftRightSheetPopWindow mPopWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dim_popupwindow);
        mButton = findViewById(R.id.btn);
        initListener();
    }

    private void initListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPopWindow == null) {
                    initPopupWindow();
                }
                mPopWindow.show();
            }
        });
    }

    private void initPopupWindow() {
        mPopWindow = new BottomLeftRightSheetPopWindow.Builder(this)
                .setLeft(R.drawable.home_select, "拍照", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(DimPopupWindowActivity.this, "拍照", Toast.LENGTH_SHORT).show();
                        mPopWindow.dismiss();
                    }
                })
                .setRight(R.drawable.addpeople_select, "从相册选取", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(DimPopupWindowActivity.this, "从相册选取", Toast.LENGTH_SHORT).show();
                        mPopWindow.dismiss();
                    }
                })
                .create();
    }
}
