package com.john.newtest;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.john.newtest.activities.HotFixActivity;
import com.john.newtest.databinding.ActivityMainBinding;
import com.john.newtest.utils.Launcher;

public class MainActivity extends BaseActivity {

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(mBinding.getRoot());

        Launcher.with(getActivity(), HotFixActivity.class).execute();
    }
}
