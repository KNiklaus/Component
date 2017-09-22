package com.example.niklaus.component.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niklaus.component.R;
import com.example.niklaus.component.ui.fragment.AddPeopleFragment;
import com.example.niklaus.component.ui.fragment.HomeFragment;
import com.example.niklaus.dagger.widget.BottomBar;
import com.example.niklaus.dagger.widget.BottomBarTab;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Niklaus on 2017/9/18.
 */

public class BottomBarActivity extends AppCompatActivity {
    private final static int INDEX_HOME = 0;
    private final static int INDEX_ADDPEOPLE = 1;

    private BottomBar mBottomBar;
    private Fragment[] mFragments = new Fragment[2];
    private String[] mTags = {"Tag_HomeFragment", "Tag_AddPeopleFragment"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        mBottomBar = findViewById(R.id.bottom_bar);
        initView();
        initListener();
        initFragment();
    }

    private void initFragment() {
        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag(mTags[INDEX_HOME]);
        if (homeFragment == null) {
            mFragments[INDEX_HOME] = HomeFragment.newInstance();
            mFragments[INDEX_ADDPEOPLE] = AddPeopleFragment.newInstance();
            loadMultiFragment(getSupportFragmentManager(), R.id.fl_placeholder, INDEX_HOME, mFragments);
        } else {
            mFragments[INDEX_HOME] = getSupportFragmentManager().findFragmentByTag(mTags[INDEX_HOME]);
            mFragments[INDEX_ADDPEOPLE] = getSupportFragmentManager().findFragmentByTag(mTags[INDEX_ADDPEOPLE]);
        }
    }

    private void loadMultiFragment(FragmentManager fragmentManager, int containerId, int showPosition, Fragment... fragments) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            transaction.add(containerId,fragments[i],mTags[i]);
            if (i != showPosition) {
                transaction.hide(fragments[i]);
            }
        }
        transaction.commitAllowingStateLoss();
    }

    private void initListener() {
        mBottomBar.setOnTabSelectListener(new BottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                switchFragment(position, prePosition);
            }

            @Override
            public void onTabUnSelected(int position) {

            }

            @Override
            public void onTabReSelected(int position) {

            }
        });
    }

    private void switchFragment(int position, int prePosition) {
        getSupportFragmentManager().beginTransaction()
                .show(mFragments[position])
                .hide(mFragments[prePosition])
                .commitAllowingStateLoss();
    }


    private void initView() {
        int textSelectColor = Color.parseColor("#f4ea2a");
        int textUnSelectColor = Color.parseColor("#bfbfbf");
        mBottomBar.addTab(
                BottomBarTab.newTab(this, "首页", textSelectColor, textUnSelectColor, R.drawable.home_select, R.drawable.home_unselect, new BottomBarTab.OnSelectListener() {
                    @Override
                    public void onSelected(@NotNull ImageView imageView, @NotNull TextView textView, boolean isSelect) {

                    }
                }))
                .addTab(BottomBarTab.newTab(this, "添加员工", textSelectColor, textUnSelectColor, R.drawable.addpeople_select, R.drawable.addpeople_unselect, new BottomBarTab.OnSelectListener() {
                    @Override
                    public void onSelected(@NotNull ImageView imageView, @NotNull TextView textView, boolean isSelect) {

                    }
                }));
    }


}
