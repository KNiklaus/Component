package com.example.niklaus.component.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.niklaus.component.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnSwitchTagView(View view) {
        startActivity(new Intent(this,SwitchTagViewActivity.class));
    }

    public void btnBottomBar(View view) {
        startActivity(new Intent(this,BottomBarActivity.class));
    }

    public void btnBottomPopupWindow(View view) {
        startActivity(new Intent(this,DimPopupWindowActivity.class));
    }
}
