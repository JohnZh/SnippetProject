package com.john.newtest;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.john.common.BaseActivity;
import com.john.jrouter.JRouter;
import com.john.jrouter.annotation.Route;
import com.john.newtest.databinding.ActivityMainBinding;

@Route("/app/main")
public class MainActivity extends BaseActivity {

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(mBinding.getRoot());

        JRouter.get().message("/app/testRoute").route();
    }

}
