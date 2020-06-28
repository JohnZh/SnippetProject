package com.john.newtest;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by JohnZh on 2020/6/23
 *
 * <p></p>
 */
public class BaseActivity extends AppCompatActivity {

    protected Activity getActivity() {
        return this;
    }
}
