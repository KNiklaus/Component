package com.example.niklaus.component.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.niklaus.component.R;

/**
 * Created by Niklaus on 2017/9/18.
 */

public class AddPeopleFragment extends Fragment {
    public static AddPeopleFragment newInstance() {
        return new AddPeopleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addpeople, container, false);
        return view;
    }
}
