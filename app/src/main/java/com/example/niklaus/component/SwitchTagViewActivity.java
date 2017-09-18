package com.example.niklaus.component;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.library.switchview.SwitchTagView;

/**
 * Created by Niklaus on 2017/9/18.
 */

public class SwitchTagViewActivity extends AppCompatActivity {
    private SwitchTagView mTagView;
    private boolean isShow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_tag_view);
        mTagView = findViewById(R.id.switch_view);
    }

    public void btnClick(View view) {
        if (isShow) {
            mTagView.show();
            isShow = !isShow;
        } else {
            mTagView.hide();
            isShow = !isShow;
        }
    }
}
